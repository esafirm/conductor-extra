package nolambda.screen

import android.os.Bundle
import android.os.Parcelable
import com.esafirm.conductorextra.addLifecycleCallback
import io.reactivex.functions.Consumer

abstract class StatefulScreen<State> : BaseScreen {

    companion object {
        private const val KEY_STATE = "SCREEN_STATE"
    }

    constructor() : super()
    constructor(bundle: Bundle?) : super(bundle)

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            screenPresenter.stateSubject.subscribe(this, Consumer { render(it) })
            remover()
        })
    }

    protected lateinit var screenPresenter: Presenter<State>

    override fun onSaveInstanceState(outState: Bundle) {
        val state = screenPresenter.stateSubject.value
        if (state != null && state is Parcelable) {
            outState.putParcelable(KEY_STATE, state)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val state = savedInstanceState.getParcelable<Parcelable>(KEY_STATE)
        if (state != null) {
            screenPresenter.stateSubject.postValue(state as State)
        }
    }

    abstract fun render(state: State)
}
