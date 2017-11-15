package com.esafirm.conductorextra.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.addLifecycleCallback
import com.esafirm.conductorextra.markSavedState

abstract class BaseController<BindingResult> : Controller {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    init {
        addLifecycleCallback(
                onSaveViewState = { _, outState, _ -> outState.markSavedState() },
                onPreCreateView = { _, _ -> onSetupComponent() }
        )
    }

    /* --------------------------------------------------- */
    /* > To be overrode */
    /* --------------------------------------------------- */

    abstract fun getLayoutResId(): Int
    abstract fun onViewBound(bindingResult: BindingResult, savedState: Bundle?)
    abstract fun getBinder(): ControllerBinder<BindingResult>

    open fun onSetupComponent() {}

    /* --------------------------------------------------- */
    /* > Lifecycle */
    /* --------------------------------------------------- */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(getLayoutResId(), container, false)
                    .also {
                        bindView(it)
                    }

    open protected fun bindView(view: View) {
        var savedInstanceState: Bundle? = null
        addLifecycleCallback(
                onRestoreViewState = { _, state, _ -> savedInstanceState = state },
                onPreAttach = { _, _, remover ->
                    onViewBound(getBinder().bind(view), savedInstanceState)
                    remover()
                }
        )
    }
}
