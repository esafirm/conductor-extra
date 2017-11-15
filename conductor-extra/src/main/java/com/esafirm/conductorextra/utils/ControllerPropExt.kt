package com.esafirm.conductorextra.utils

import com.bluelinelabs.conductor.Controller
import kotlin.properties.ReadWriteProperty

fun <T : Any> Controller.resetOnDetach(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerEvent.DETACH)

fun <T : Any> Controller.resetOnDestroyView(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerEvent.DESTROY_VIEW)

