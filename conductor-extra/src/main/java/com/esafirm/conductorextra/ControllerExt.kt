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

fun Controller.getTopController(): Controller = router.getTopController()

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

/* --------------------------------------------------- */
/* > Lifecycle */
/* --------------------------------------------------- */

typealias Action = Controller.() -> Unit
fun Controller.addLifecycleCallback(
        onPreDestroy: Action? = null,
        onPostAttach: Action? = null,
        onPreDetach: Action? = null
) {
    addLifecycleListener(object : Controller.LifecycleListener() {

        override fun postAttach(controller: Controller, view: View) {
            onPostAttach?.run { invoke(controller) }
        }

        override fun preDetach(controller: Controller, view: View) {
            onPreDetach?.run { invoke(controller) }
        }

        override fun preDestroy(controller: Controller) {
            onPreDestroy?.run { invoke(controller) }
        }
    })
}
