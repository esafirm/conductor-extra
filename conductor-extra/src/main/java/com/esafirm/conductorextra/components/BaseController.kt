package com.esafirm.conductorextra.components

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.esafirm.conductorextra.addLifecycleCallback
import com.esafirm.conductorextra.components.configs.ControllerConfigManager
import com.esafirm.conductorextra.markSavedState

abstract class BaseController<BindingResult> : RestoreViewOnCreateController {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?) =
            getLayoutView(container).also { bindView(it, savedViewState) }

    protected open fun bindView(view: View, savedViewState: Bundle?) {
        addLifecycleCallback(onPreAttach = { _, _, remover ->
            onViewBound(getBinder().bind(view), savedViewState)
            remover()
        })
    }

    /* --------------------------------------------------- */
    /* > Helper */
    /* --------------------------------------------------- */

    protected fun ViewGroup.inflate(@LayoutRes resId: Int) =
            LayoutInflater.from(context).inflate(resId, this, false)!!
}
