package com.esafirm.conductorextra.butterknife

import android.os.Bundle
import android.view.View
import com.esafirm.conductorextra.components.BaseController
import com.esafirm.conductorextra.components.ControllerBinder

abstract class AbsController : BaseController<View> {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > Binder */
    /* --------------------------------------------------- */

    override fun getBinder(): ControllerBinder<View> = object : ControllerBinder<View> {
        override fun bind(view: View): View {
            return ButterknifeBinder.bind(this@AbsController, view, this@AbsController)
        }
    }
}
