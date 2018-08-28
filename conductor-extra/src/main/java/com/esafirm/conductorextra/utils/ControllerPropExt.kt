package com.esafirm.conductorextra.utils

import com.bluelinelabs.conductor.Controller
import kotlin.properties.ReadWriteProperty

fun <T : Any> Controller.resetOnDetach(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerEvent.POST_DETACH)

fun <T : Any> Controller.resetOnDestroyView(): ReadWriteProperty<Controller, T> =
        ControllerProp(this, ControllerEvent.POST_DESTROY_VIEW)

