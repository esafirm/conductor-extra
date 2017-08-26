package com.esafirm.conductorextra.databinding

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.esafirm.conductorextra.components.BaseController
import com.esafirm.conductorextra.components.ControllerBinder

abstract class BinderController<Binding : ViewDataBinding> : BaseController<Binding> {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > View Binding */
    /* --------------------------------------------------- */

    override fun getBinder(): ControllerBinder<Binding> = DataBindingBinder.createBinder()
}
