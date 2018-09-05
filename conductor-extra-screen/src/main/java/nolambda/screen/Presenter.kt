package nolambda.screen

import android.arch.lifecycle.LifecycleOwner
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import nolambda.statesubject.StateSubject

internal typealias StateMutator<State> = (State) -> State

abstract class Presenter<State> {

    open val stateSubject: StateSubject<State> = StateSubject<State>().apply { postValue(initialState()) }

    private val internalQueue by lazy {
        PublishSubject.create<StateMutator<State>>()
    }

    private var alreadyListenInternalQueue = false

    protected abstract fun initialState(): State

    protected fun setState(async: Boolean = true, mutator: StateMutator<State>) {
        if (async) {
            postAsyncValue(mutator)
        } else {
            stateSubject.postValue(mutator(stateSubject.value))
        }
    }

    private fun postAsyncValue(mutator: StateMutator<State>) {
        if (!alreadyListenInternalQueue) {
            alreadyListenInternalQueue = true
            internalQueue
                    .subscribeOn(Schedulers.io())
                    .map { it.invoke(stateSubject.value) }
                    .subscribe {
                        stateSubject.postValue(it)
                    }
        }
        internalQueue.onNext(mutator)
    }

    open fun bind(lifecycleOwner: LifecycleOwner) {
        // deliberately no-op
    }
}
