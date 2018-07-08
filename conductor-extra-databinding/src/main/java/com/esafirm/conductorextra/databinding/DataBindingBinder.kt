package com.esafirm.conductorextra.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import com.esafirm.conductorextra.components.ControllerBinder

object DataBindingBinder {
    fun <T : ViewDataBinding> createBinder(): ControllerBinder<T> = object : ControllerBinder<T> {
        override fun bind(view: View): T {
            return DataBindingUtil.bind(view) ?: throw IllegalStateException("Bound view must not be null")
        }
    }
}
