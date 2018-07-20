package com.esafirm.conductorextra.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.esafirm.conductorextra.components.ControllerBinder

object DataBindingBinder {
    fun <T : ViewDataBinding> createBinder(isDialog: Boolean = false): ControllerBinder<T> = object : ControllerBinder<T> {
        override fun bind(view: View): T {
            val toBeBoundView = when (isDialog) {
                true -> view.let { it as ViewGroup }.getChildAt(0)
                false -> view
            }
            return DataBindingUtil.bind(toBeBoundView) ?: throw IllegalStateException("Bound view must not be null")
        }
    }
}
