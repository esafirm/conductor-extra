package com.esafirm.conductorextra.common

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler

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
typealias PairCallback<P> = (Pair<P, LifecycleRemover>) -> Unit
typealias SingleCallback = (LifecycleRemover) -> Unit

fun Controller.onEvent(

        onPreContextAvailable: SingleCallback? = null,
        onPostContextAvailable: SingleCallback? = null,

        onPreContextUnavailable: PairCallback<Context>? = null,
        onPostContextUnavalable: SingleCallback? = null,

        onPreCreateView: SingleCallback? = null,
        onPostCreateView: SingleCallback? = null,

        onPreDestroy: SingleCallback? = null,
        onPostDestroy: SingleCallback? = null,

        onPreDestroyView: SingleCallback? = null,
        onPostDestroyView: SingleCallback? = null,

        onPreAttach: SingleCallback? = null,
        onPostAttach: SingleCallback? = null,

        onPostDetach: SingleCallback? = null,
        onPreDetach: SingleCallback? = null,

        onSaveInstanceState: PairCallback<Bundle>? = null,
        onRestoreInstanceState: PairCallback<Bundle>? = null,

        onSaveViewState: PairCallback<Bundle>? = null,
        onRestoreViewState: PairCallback<Bundle>? = null

): Controller.LifecycleListener {

    val listener = object : Controller.LifecycleListener() {

        private val remover: LifecycleRemover by lazy(LazyThreadSafetyMode.NONE) {
            { removeLifecycleListener(this) }
        }

        override fun preContextAvailable(controller: Controller) {
            onPreContextAvailable?.invoke(remover)
        }

        override fun postContextAvailable(controller: Controller, context: Context) {
            onPostContextAvailable?.invoke(remover)
        }

        override fun preContextUnavailable(controller: Controller, context: Context) {
            onPreContextUnavailable?.invoke(context to remover)
        }

        override fun postContextUnavailable(controller: Controller) {
            onPostContextUnavalable?.invoke(remover)
        }

        override fun preCreateView(controller: Controller) {
            onPreCreateView?.invoke(remover)
        }

        override fun postCreateView(controller: Controller, view: View) {
            onPostCreateView?.invoke(remover)
        }

        override fun onSaveViewState(controller: Controller, outState: Bundle) {
            onSaveViewState?.invoke(outState to remover)
        }

        override fun onRestoreViewState(controller: Controller, savedViewState: Bundle) {
            onRestoreViewState?.invoke(savedViewState to remover)
        }

        override fun onRestoreInstanceState(controller: Controller, savedInstanceState: Bundle) {
            onRestoreInstanceState?.invoke(savedInstanceState to remover)
        }

        override fun onSaveInstanceState(controller: Controller, outState: Bundle) {
            onSaveInstanceState?.invoke(outState to remover)
        }

        override fun preAttach(controller: Controller, view: View) {
            onPreAttach?.invoke(remover)
        }

        override fun postAttach(controller: Controller, view: View) {
            onPostAttach?.invoke(remover)
        }

        override fun preDetach(controller: Controller, view: View) {
            onPreDetach?.invoke(remover)
        }

        override fun postDetach(controller: Controller, view: View) {
            onPostDetach?.invoke(remover)
        }

        override fun preDestroy(controller: Controller) {
            onPreDestroy?.invoke(remover)
        }

        override fun postDestroy(controller: Controller) {
            onPostDestroy?.invoke(remover)
        }

        override fun preDestroyView(controller: Controller, view: View) {
            onPreDestroyView?.invoke(remover)
        }

        override fun postDestroyView(controller: Controller) {
            onPostDestroyView?.invoke(remover)
        }
    }
    addLifecycleListener(listener)

    return listener
}
