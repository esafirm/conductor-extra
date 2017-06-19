package com.conductor.esafirm.com.conductorextra.transaction

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler

object Routes {

    fun simpleTransaction(controller: Controller, changeHandler: ControllerChangeHandler): RouterTransaction {
        val popChangeHandler = try {
            changeHandler.javaClass.newInstance()
        } catch (e: Exception) {
            VerticalChangeHandler()
        }
        return RouterTransaction.with(controller)
                .pushChangeHandler(changeHandler)
                .popChangeHandler(popChangeHandler)
    }
}
