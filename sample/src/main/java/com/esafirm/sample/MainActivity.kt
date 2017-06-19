package com.conductor.esafirm.com.sample

import com.bluelinelabs.conductor.Controller

import com.conductor.esafirm.com.conductorextra.components.ControllerActivity

class MainActivity : ControllerActivity() {

    override fun getController(): Controller = MainController()
}
