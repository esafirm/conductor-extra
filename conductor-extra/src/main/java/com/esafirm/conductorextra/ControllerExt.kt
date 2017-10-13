package com.esafirm.conductorextra

import android.os.Bundle
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

typealias Callback<P1, P2> = (P1, P2) -> Unit

fun Controller.addLifecycleCallback(
        onPreDestroy: ((Controller) -> Unit)? = null,
        onPostDestroy: ((Controller) -> Unit)? = null,
        onPostAttach: Callback<Controller, View>? = null,
        onPostDetach: Callback<Controller, View>? = null,
        onPreDetach: Callback<Controller, View>? = null,
        onSaveInstanceState: Callback<Controller, Bundle>? = null,
        onRestoreInstanceState: Callback<Controller, Bundle>? = null,
        onSaveViewState: Callback<Controller, Bundle>? = null,
        onRestoreViewState: Callback<Controller, Bundle>? = null
) {
    addLifecycleListener(object : Controller.LifecycleListener() {

        override fun onSaveViewState(controller: Controller, outState: Bundle) {
            onSaveViewState?.invoke(controller, outState)
        }

        override fun onRestoreViewState(controller: Controller, savedViewState: Bundle) {
            onRestoreViewState?.invoke(controller, savedViewState)
        }

        override fun onRestoreInstanceState(controller: Controller, savedInstanceState: Bundle) {
            onRestoreInstanceState?.invoke(controller, savedInstanceState)
        }

        override fun onSaveInstanceState(controller: Controller, outState: Bundle) {
            onSaveInstanceState?.invoke(controller, outState)
        }

        override fun postAttach(controller: Controller, view: View) {
            onPostAttach?.invoke(controller, view)
        }

        override fun preDetach(controller: Controller, view: View) {
            onPreDetach?.invoke(controller, view)
        }

        override fun postDetach(controller: Controller, view: View) {
            onPostDetach?.invoke(controller, view)
        }

        override fun preDestroy(controller: Controller) {
            onPreDestroy?.invoke(controller)
        }

        override fun postDestroy(controller: Controller) {
            onPostDestroy?.invoke(controller)
        }
    })
}
