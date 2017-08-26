package com.esafirm.conductorextra.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller

abstract class BaseController<BindingResult> : Controller {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > To be overrode */
    /* --------------------------------------------------- */

    abstract fun getLayoutResId(): Int
    abstract fun onViewBound(bindingResult: BindingResult)
    abstract fun getBinder(): ControllerBinder<BindingResult>

    open fun onSetupComponent() {}

    /* --------------------------------------------------- */
    /* > Lifecycle */
    /* --------------------------------------------------- */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        onSetupComponent()
        return inflater.inflate(getLayoutResId(), container, false)
                .also {
                    bindView(it)
                }
    }

    open protected fun bindView(view: View) {
        onViewBound(getBinder().bind(view))
    }
}
