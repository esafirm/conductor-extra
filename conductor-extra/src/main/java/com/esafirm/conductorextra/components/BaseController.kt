package com.esafirm.conductorextra.components

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.addLifecycleCallback
import com.esafirm.conductorextra.components.configs.ControllerConfigManager
import com.esafirm.conductorextra.markSavedState

abstract class BaseController<BindingResult> : Controller {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    init {
        val config = ControllerConfigManager.configResolver(javaClass)
        addLifecycleCallback(onSaveViewState = { _, outState, _ -> outState.markSavedState() })
        addLifecycleCallback(onPreCreateView = { _, remover ->
            onSetupComponent()
            if (config.injectOnce) {
                remover()
            }
        })
    }

    /* --------------------------------------------------- */
    /* > To be overrode */
    /* --------------------------------------------------- */

    abstract fun getLayoutView(container: ViewGroup): View
    abstract fun onViewBound(bindingResult: BindingResult, savedState: Bundle?)
    abstract fun getBinder(): ControllerBinder<BindingResult>

    open fun onSetupComponent() {}

    /* --------------------------------------------------- */
    /* > Lifecycle */
    /* --------------------------------------------------- */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            getLayoutView(container).also { bindView(it) }

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

    /* --------------------------------------------------- */
    /* > Helper */
    /* --------------------------------------------------- */

    protected fun ViewGroup.inflate(@LayoutRes resId: Int) =
            LayoutInflater.from(context).inflate(resId, this, false)!!
}
