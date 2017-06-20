package com.esafirm.conductorextra.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.esafirm.conductorextra.components.BaseController
import com.esafirm.conductorextra.components.ControllerBinder

abstract class AbsController<Binding : ViewDataBinding> : BaseController<Binding> {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > Methods */
    /* --------------------------------------------------- */

    override fun getBinder(): ControllerBinder<Binding> = object : ControllerBinder<Binding> {
        override fun bind(view: View): Binding {
            return DataBindingUtil.bind(view)
        }
    }
}
