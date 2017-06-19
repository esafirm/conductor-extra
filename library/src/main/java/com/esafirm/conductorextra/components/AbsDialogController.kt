package com.conductor.esafirm.com.conductorextra.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.conductor.esafirm.com.conductorextra.R
import com.conductor.esafirm.com.conductorextra.popCurrentController

abstract class AbsDialogController : AbsController() {

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
            postCreateView(it)
        }
    }

    protected fun handleOverlay(): Boolean = false
}
