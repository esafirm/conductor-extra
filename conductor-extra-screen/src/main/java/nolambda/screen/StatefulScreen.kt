package nolambda.screen

import android.content.Context
import android.os.Bundle
import com.esafirm.conductorextra.addLifecycleCallback
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer

abstract class StatefulScreen<STATE, PRESENTER : Presenter<STATE>> : BaseScreen {


    protected var stateTransformer: ObservableTransformer<STATE, STATE>? = null

    // Presenter that exposed
    protected lateinit var screenPresenter: () -> PRESENTER
    // Presenter to use internally
    private val presenterInternal by lazy { screenPresenter() }

    protected lateinit var stateSaver: () -> StateSaver<STATE>
    private val defaultStateSaver by lazy { DefaultStateSaver<STATE>() }
    private val internalStateSaver by lazy {
        try {
            stateSaver()
        } catch (e: Exception) {
            defaultStateSaver
        }
    }

    private var deferredState: STATE? = null

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        restoreStateIfNeeded()
    }

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            presenterInternal.bind(this)
            presenterInternal.stateSubject.subscribe(this, stateTransformer, Consumer {
                render(presenterInternal, it)
            })
            remover()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = presenterInternal.stateSubject.value
        internalStateSaver.onSaveInstanceState(state, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        deferredState = internalStateSaver.onRestoreInstanceState(savedInstanceState)
    }

    private fun restoreStateIfNeeded() {
        if (deferredState != null) {
            screenPresenter().stateSubject.postValue(deferredState)
            deferredState = null
        }
    }

    abstract fun render(presenter: PRESENTER, state: STATE)
}
