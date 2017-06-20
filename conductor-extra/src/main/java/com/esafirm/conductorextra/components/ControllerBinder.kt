package com.esafirm.conductorextra.components

import android.view.View

interface ControllerBinder<out T> {
    fun bind(view: View): T
}
