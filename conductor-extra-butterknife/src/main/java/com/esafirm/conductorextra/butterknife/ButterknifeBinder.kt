package com.esafirm.conductorextra.butterknife

import android.view.View
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.addLifecycleCallback
import com.esafirm.conductorextra.components.ControllerBinder

object ButterknifeBinder {

    fun createBinder(controller: Controller): ControllerBinder<View> = object : ControllerBinder<View> {
        override fun bind(view: View): View {
            return bind(controller, view, controller)
        }
    }

    private fun bind(target: Any, view: View, controller: Controller): View {
        ButterKnife.bind(target, view).also {
            controller.addLifecycleCallback(onPreDestroy = { it.unbind() })
        }
        return view
    }

}
