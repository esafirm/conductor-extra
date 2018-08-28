package com.esafirm.conductorextra.butterknife

import android.view.View
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.onEvent
import com.esafirm.conductorextra.components.ControllerBinder

object ButterknifeBinder {

    fun createBinder(controller: Controller): ControllerBinder<View> = object : ControllerBinder<View> {
        override fun bind(view: View): View = bind(controller, view, controller)
    }

    private fun bind(target: Any, view: View, controller: Controller): View {
        ButterKnife.bind(target, view).also { binder ->
            controller.onEvent(onPostDestroyView = { _, remover ->
                binder.unbind()
                remover()
            })
        }
        return view
    }

}
