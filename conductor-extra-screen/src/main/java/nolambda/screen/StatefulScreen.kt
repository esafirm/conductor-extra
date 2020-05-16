package nolambda.screen

import android.content.Context
import android.os.Bundle
import com.esafirm.conductorextra.common.onEvent
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer

abstract class StatefulScreen<STATE, PRESENTER : Presenter<STATE>> : BaseScreen {

    protected open var stateTransformer: ObservableTransformer<STATE, STATE>? = null

    // Presenter that exposed
    abstract fun createPresenter(): PRESENTER

    // Presenter to use internally
    private val presenterInternal by lazy { createPresenter() }

    protected lateinit var stateSaver: () -> StateSaver<STATE>
    private val stateSaverInternal by lazy {
        try {
            stateSaver()
        } catch (e: Exception) {
            DefaultStateSaver<STATE>()
        }
    }

    private var deferredStateFunc: (() -> STATE?)? = null

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        restoreStateIfNeeded()
    }

    init {
        onEvent(onPostCreateView = { remover ->
            presenterInternal.bind(this)
            presenterInternal.stateSubject.subscribe(this, stateTransformer, Consumer {
                val shouldRender = onSideEffect(presenterInternal, it)
                if (shouldRender) {
                    render(presenterInternal, it)
                }
            })
            presenterInternal.initPresenter()
            remover()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = presenterInternal.stateSubject.value
        stateSaverInternal.onSaveInstanceState(state, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        deferredStateFunc = { stateSaverInternal.onRestoreInstanceState(savedInstanceState) }
    }

    private fun restoreStateIfNeeded() {
        deferredStateFunc?.let { deferredState ->
            presenterInternal.setState { deferredState.invoke() ?: it }
            deferredStateFunc = null
        }
    }

    /**
     * Executed before [render]
     * This can be handy if you want some interceptor logic or navigation logic
     * @return True if you want to [render]
     */
    open fun onSideEffect(presenter: PRESENTER, state: STATE): Boolean = true

    abstract fun render(presenter: PRESENTER, state: STATE)
}
