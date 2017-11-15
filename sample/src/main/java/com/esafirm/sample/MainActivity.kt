package com.esafirm.sample

import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.components.ControllerActivity

class MainActivity : ControllerActivity() {
    override fun getController(): Controller = MainController()
}
