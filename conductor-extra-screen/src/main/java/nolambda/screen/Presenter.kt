package nolambda.screen

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import nolambda.statesubject.StateSubject
import java.util.concurrent.Executors

internal typealias StateMutator<State> = (State) -> State

abstract class Presenter<State> {

    private val executors by lazy { Executors.newFixedThreadPool(1) }

    open val stateSubject: StateSubject<State> by lazy(LazyThreadSafetyMode.NONE) {
        StateSubject<State>().apply { postValue(initialState()) }
    }

    private val internalQueue by lazy {
        PublishSubject.create<StateMutator<State>>()
    }

    private var alreadyListenInternalQueue = false

    protected abstract fun initialState(): State

    fun setState(async: Boolean = true, mutator: StateMutator<State>) {
        if (async) {
            postAsyncValue(mutator)
        } else {
            stateSubject.postValue(mutator(stateSubject.value))
        }
    }

    @SuppressLint("CheckResult")
    private fun postAsyncValue(mutator: StateMutator<State>) {
        if (!alreadyListenInternalQueue) {
            alreadyListenInternalQueue = true
            internalQueue
                    .subscribeOn(Schedulers.from(executors))
                    .observeOn(Schedulers.from(executors))
                    .map { it.invoke(stateSubject.value) }
                    .subscribe { newState ->
                        if (newState != stateSubject.value) {
                            stateSubject.postValue(newState)
                        }
                    }
        }
        internalQueue.onNext(mutator)
    }

    open fun bind(lifecycleOwner: LifecycleOwner) {
        // deliberately no-op
    }

    /** This called after [bind] and [stateSubject] subscribe done
     *  On [com.bluelinelabs.conductor.Controller] level,
     *  this function is called after [com.bluelinelabs.conductor.Controller.onCreateView]
     **/
    open fun initPresenter() {
        // deliberately no-op
    }
}
