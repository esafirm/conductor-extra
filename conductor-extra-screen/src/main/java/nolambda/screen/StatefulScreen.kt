package nolambda.screen

import android.content.Context
import android.os.Bundle
import com.esafirm.conductorextra.addLifecycleCallback
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer

abstract class StatefulScreen<STATE, PRESENTER : Presenter<STATE>> : BaseScreen {

    protected lateinit var screenPresenter: () -> PRESENTER
    protected var stateTransformer: ObservableTransformer<STATE, STATE>? = null

    private var deferredState: STATE? = null

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        restoreStateIfNeeded()
    }

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            val presenter = screenPresenter()
            presenter.bind(this)
            presenter.stateSubject.subscribe(this, stateTransformer, Consumer {
                render(presenter, it)
            })
            remover()
        })
    }

    private val defaultStateSaver by lazy { DefaultStateSaver<STATE>() }
    protected var stateSaver: StateSaver<STATE>? = null
        get() = field ?: defaultStateSaver


    override fun onSaveInstanceState(outState: Bundle) {
        val state = screenPresenter().stateSubject.value
        stateSaver?.onSaveInstanceState(state, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        deferredState = stateSaver?.onRestoreInstanceState(savedInstanceState)
    }

    private fun restoreStateIfNeeded() {
        if (deferredState != null) {
            screenPresenter().stateSubject.postValue(deferredState)
            deferredState = null
        }
    }

    abstract fun render(presenter: PRESENTER, state: STATE)
}
