package com.esafirm.conductorextra.butterknife

import android.view.View
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.addLifecycleCallback

object ButterknifeBinder {

    fun bind(target: Any, view: View, controller: Controller): View {
        ButterKnife.bind(target, view).also {
            controller.addLifecycleCallback(onPreDestroy = { it.unbind() })
        }
        return view
    }

}
