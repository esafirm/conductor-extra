package com.esafirm.conductorextra.utils

import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.common.onEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ControllerProp<T : Any>(controller: Controller, private val event: ControllerEvent)
    : ReadWriteProperty<Controller, T> {

    private var value: T? = null

    init {
        when (event) {
            ControllerEvent.POST_DESTROY_VIEW -> {
                controller.onEvent(onPostDestroyView = { _ -> value = null })
            }
            ControllerEvent.POST_DETACH -> {
                controller.onEvent(onPostDetach = { _ -> value = null })
            }
        }
    }

    override fun getValue(thisRef: Controller, property: KProperty<*>) = value
            ?: throw IllegalStateException("Property ${property.name}" +
                    " should be initialized before get and not called after $event")

    override fun setValue(thisRef: Controller, property: KProperty<*>, value: T) {
        this.value = value
    }
}


