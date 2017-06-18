package extra.conductor.esafirm.com.conductorextra

import com.bluelinelabs.conductor.Router

fun Router.handleBackWithMinBackStack(minBackStack: Int): Boolean {
    return isHaveMoreBackStack(this, minBackStack) && handleBack()
}

private fun isHaveMoreBackStack(router: Router, minBackStack: Int): Boolean {
    if (router.backstackSize > minBackStack) {
        return true
    }
    return router.backstack
            .flatMap { it.controller().childRouters }
            .any { isHaveMoreBackStack(it, minBackStack) }
}

