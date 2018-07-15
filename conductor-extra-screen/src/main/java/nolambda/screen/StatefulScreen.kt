package nolambda.screen

import android.os.Bundle
import com.esafirm.conductorextra.addLifecycleCallback
import io.reactivex.functions.Consumer

abstract class StatefulScreen<State> : BaseScreen {

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            screenPresenter.stateSubject.subscribe(this, Consumer { render(it) })
            remover()
        })
    }

    protected lateinit var screenPresenter: Presenter<State>

    private val defaultStateSaver by lazy { DefaultStateSaver<State>() }
    protected var stateSaver: StateSaver<State>? = null
        get() = field ?: defaultStateSaver


    override fun onSaveInstanceState(outState: Bundle) {
        val state = screenPresenter.stateSubject.value
        stateSaver?.onSaveInstanceState(state, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val state = stateSaver?.onRestoreInstanceState(savedInstanceState)
        if (state != null) {
            screenPresenter.stateSubject.postValue(state)
        }
    }

    abstract fun render(state: State)
}
