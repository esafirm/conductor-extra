package com.esafirm.sample

import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.components.ControllerActivity
import com.esafirm.sample.screen.SampleStateScreen

class MainActivity : ControllerActivity() {
    override fun getController(): Controller = SampleStateScreen()
}
