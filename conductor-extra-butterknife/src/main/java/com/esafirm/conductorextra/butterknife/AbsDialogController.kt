package com.esafirm.conductorextra.butterknife

import android.os.Bundle
import android.view.View
import com.esafirm.conductorextra.components.BaseDialogController
import com.esafirm.conductorextra.components.ControllerBinder

abstract class AbsDialogController : BaseDialogController<View> {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > View Binder */
    /* --------------------------------------------------- */

    override fun getBinder(): ControllerBinder<View> = ButterknifeBinder.createBinder(this)
}
