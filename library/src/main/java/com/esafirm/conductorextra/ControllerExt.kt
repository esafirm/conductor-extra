package com.esafirm.conductorextra

import android.view.View
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.esafirm.conductorextra.transaction.Routes

fun Controller.callWhenReady(onReady: Controller.() -> Unit) =
        addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                onReady()
                removeLifecycleListener(this)
            }
        })

fun Controller.getTopController(router: Router): Controller =
        router.backstack[router.backstackSize - 1].controller()

fun Controller.finishActivity() = activity?.finish()

fun Controller.popCurrentController() {
    if (router.backstackSize > 0) {
        router.popCurrentController()
    }
}

fun Controller.showDialog(childRouter: Router, controller: Controller) {
    childRouter.setPopsLastView(true)
            .pushController(Routes.simpleTransaction(
                    controller,
                    FadeChangeHandler()
            ))
}
