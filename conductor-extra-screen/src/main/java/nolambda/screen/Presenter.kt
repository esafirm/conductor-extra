package nolambda.screen

import nolambda.statesubject.StateSubject

abstract class Presenter<State> {

    abstract val stateSubject: StateSubject<State>

    protected fun changeState(mutator: (State) -> State) {
        stateSubject.postValue(mutator(stateSubject.value))
    }
}
