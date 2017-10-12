package com.esafirm.conductorextra

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router

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

