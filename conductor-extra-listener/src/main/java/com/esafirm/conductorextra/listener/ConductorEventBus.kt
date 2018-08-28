package com.esafirm.conductorextra.listener

import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.onEvent
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

data class NamedEvent(
        val name: String,
        val value: Any
)

private val bus = PublishRelay.create<Any>().toSerialized()
private val listenerMap: MutableMap<Controller, Boolean> = mutableMapOf()

fun Controller.postEvent(any: Any) {
    bus.accept(NamedEvent(this.javaClass.canonicalName, any))
}

@Suppress("UNCHECKED_CAST")
fun <T> Controller.onEventObservable(): Observable<T> {
    val name = javaClass.canonicalName
    return bus.filter { it is NamedEvent && it.name == name }
            .cast(NamedEvent::class.java)
            .map { it.value as T }
}

fun <T> Controller.onEvent(block: (T) -> Unit) {
    val controller = this
    val isBound = listenerMap[controller] ?: false
    if (isBound) {
        return
    }

    var disposable: Disposable? = null
    val bindController = {
        val bound = listenerMap[controller] ?: false
        if (!bound) {
            disposable?.dispose()
            disposable = onEventObservable<T>().subscribe { block(it) }
            listenerMap.put(this, true)
        }
    }

    bindController()

    onEvent(
            onPostAttach = { _, _, _ -> bindController() },
            onPostDetach = { _, _, _ ->
                disposable?.dispose()
                listenerMap[controller] = false
            },
            onPreDestroy = { _, remover ->
                remover()
                listenerMap.remove(controller)
            }
    )
}
