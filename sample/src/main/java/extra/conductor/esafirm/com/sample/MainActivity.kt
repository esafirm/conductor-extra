package extra.conductor.esafirm.com.sample

import com.bluelinelabs.conductor.Controller
import extra.conductor.esafirm.com.conductorextra.components.AbsController

import extra.conductor.esafirm.com.conductorextra.components.ControllerActivity

class MainActivity : ControllerActivity() {

    override fun getController(): Controller = MainController()
}
