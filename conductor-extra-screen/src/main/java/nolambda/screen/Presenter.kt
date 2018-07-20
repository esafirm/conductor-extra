package nolambda.screen

import android.arch.lifecycle.LifecycleOwner
import nolambda.statesubject.StateSubject

abstract class Presenter<State> {

    open val stateSubject: StateSubject<State> = StateSubject<State>().apply { postValue(initialState()) }

    protected abstract fun initialState(): State

    protected fun changeState(mutator: (State) -> State) {
        stateSubject.postValue(mutator(stateSubject.value))
    }

    open fun bind(lifecycleOwner: LifecycleOwner) {
        // deliberately no-op
    }
}
