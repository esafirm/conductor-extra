package com.esafirm.conductorextra.utils

import com.bluelinelabs.conductor.Controller
import kotlin.properties.ReadWriteProperty

fun <T : Any> Controller.resetOnDetach(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerPropEvent.DETACH)

fun <T : Any> Controller.resetOnDestroyView(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerPropEvent.DESTROY_VIEW)

