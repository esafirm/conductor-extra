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

typealias LifecycleRemover = () -> Unit
typealias Callback<P1, P2> = (P1, P2, LifecycleRemover) -> Unit
typealias SingleCallback<P1> = (P1, LifecycleRemover) -> Unit

fun Controller.addLifecycleCallback(

        onPreCreateView: SingleCallback<Controller>? = null,
        onPostCreateView: Callback<Controller, View>? = null,

        onPreDestroy: SingleCallback<Controller>? = null,
        onPostDestroy: SingleCallback<Controller>? = null,

        onPreDestroyView: Callback<Controller, View>? = null,
        onPostDestroyView: SingleCallback<Controller>? = null,

        onPreAttach: Callback<Controller, View>? = null,
        onPostAttach: Callback<Controller, View>? = null,

        onPostDetach: Callback<Controller, View>? = null,
        onPreDetach: Callback<Controller, View>? = null,

        onSaveInstanceState: Callback<Controller, Bundle>? = null,
        onRestoreInstanceState: Callback<Controller, Bundle>? = null,

        onSaveViewState: Callback<Controller, Bundle>? = null,
        onRestoreViewState: Callback<Controller, Bundle>? = null

): Controller.LifecycleListener {

    val listener = object : Controller.LifecycleListener() {

        private val remover: LifecycleRemover = { removeLifecycleListener(this) }

        override fun preCreateView(controller: Controller) {
            onPreCreateView?.invoke(controller, remover)
        }

        override fun postCreateView(controller: Controller, view: View) {
            onPostCreateView?.invoke(controller, view, remover)
        }

        override fun onSaveViewState(controller: Controller, outState: Bundle) {
            onSaveViewState?.invoke(controller, outState, remover)
        }

        override fun onRestoreViewState(controller: Controller, savedViewState: Bundle) {
            onRestoreViewState?.invoke(controller, savedViewState, remover)
        }

        override fun onRestoreInstanceState(controller: Controller, savedInstanceState: Bundle) {
            onRestoreInstanceState?.invoke(controller, savedInstanceState, remover)
        }

        override fun onSaveInstanceState(controller: Controller, outState: Bundle) {
            onSaveInstanceState?.invoke(controller, outState, remover)
        }

        override fun preAttach(controller: Controller, view: View) {
            onPreAttach?.invoke(controller, view, remover)
        }

        override fun postAttach(controller: Controller, view: View) {
            onPostAttach?.invoke(controller, view, remover)
        }

        override fun preDetach(controller: Controller, view: View) {
            onPreDetach?.invoke(controller, view, remover)
        }

        override fun postDetach(controller: Controller, view: View) {
            onPostDetach?.invoke(controller, view, remover)
        }

        override fun preDestroy(controller: Controller) {
            onPreDestroy?.invoke(controller, remover)
        }

        override fun postDestroy(controller: Controller) {
            onPostDestroy?.invoke(controller, remover)
        }

        override fun preDestroyView(controller: Controller, view: View) {
            onPreDestroyView?.invoke(controller, view, remover)
        }

        override fun postDestroyView(controller: Controller) {
            onPostDestroyView?.invoke(controller, remover)
        }
    }
    addLifecycleListener(listener)

    return listener
}
