package com.esafirm.conductorextra.common

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler

fun Router.getTopController(): Controller =
        backstack[backstackSize - 1].controller()

fun Router.handleBackWithMinBackStack(minBackStack: Int): Boolean =
        isHaveMoreBackStack(this, minBackStack) && handleBack()

private fun isHaveMoreBackStack(router: Router, minBackStack: Int): Boolean {
    if (router.backstackSize > minBackStack) {
        return true
    }
    return router.backstack
            .flatMap { it.controller().childRouters }
            .any { isHaveMoreBackStack(it, minBackStack) }
}

fun Router.pushTo(
        controller: Controller,
        changeHandler: ControllerChangeHandler = VerticalChangeHandler(),
        popLastView: Boolean = false): Controller {

    when (popLastView) {
        true -> setPopsLastView(true)
        false -> this
    }.pushController(Routes.simpleTransaction(
            controller,
            changeHandler
    ))

    return controller
}

fun Router.setRootIfNeeded(controller: Controller): Controller {
    if (!hasRootController()) {
        setRoot(RouterTransaction.with(controller))
    }
    return controller
}
