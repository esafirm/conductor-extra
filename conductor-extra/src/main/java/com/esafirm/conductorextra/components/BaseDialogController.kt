package com.esafirm.conductorextra.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esafirm.conductorextra.R
import com.esafirm.conductorextra.popCurrentController

abstract class BaseDialogController<BindingResult> : BaseController<BindingResult> {

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > Methods */
    /* --------------------------------------------------- */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        onSetupComponent()

        val overlay = inflater.inflate(R.layout.ce_controller_abs_dialog, container, false) as ViewGroup
        val view = inflater.inflate(getLayoutResId(), overlay, false)

        overlay.setOnClickListener {
            if (!handleOverlay()) {
                popCurrentController()
            }
        }

        overlay.addView(view)
        return overlay.also {
            bindView(it)
        }
    }

    protected fun handleOverlay(): Boolean = false
}
