package nolambda.screen

import android.arch.lifecycle.Lifecycle.Event
import android.arch.lifecycle.Lifecycle.State
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.view.View
import com.bluelinelabs.conductor.Controller

class ControllerLifecycleRegistryOwner(controller: Controller) : Controller.LifecycleListener(), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.handleLifecycleEvent(Event.ON_CREATE)
        lifecycleRegistry.markState(State.CREATED)

        val isRetainView = {
            controller.retainViewMode == Controller.RetainViewMode.RETAIN_DETACH
        }

        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun preCreateView(controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Event.ON_START)
            }

            override fun postCreateView(controller: Controller, view: View) {
                lifecycleRegistry.markState(State.STARTED)
            }

            override fun preAttach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Event.ON_RESUME)
            }

            override fun postAttach(controller: Controller, view: View) {
                lifecycleRegistry.markState(State.RESUMED)
            }

            override fun preDetach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Event.ON_PAUSE)
            }

            override fun postDetach(controller: Controller, view: View) {
                lifecycleRegistry.markState(State.STARTED)
            }

            override fun preDestroyView(controller: Controller, view: View) {
                if (isRetainView().not()) {
                    lifecycleRegistry.handleLifecycleEvent(Event.ON_STOP)
                }
            }

            override fun postDestroyView(controller: Controller) {
                // Based on documentation
                lifecycleRegistry.markState(State.CREATED)
            }

            override fun preDestroy(controller: Controller) {
                if (isRetainView()) {
                    lifecycleRegistry.handleLifecycleEvent(Event.ON_STOP)
                }
                lifecycleRegistry.handleLifecycleEvent(Event.ON_DESTROY)
            }

            override fun postDestroy(controller: Controller) {
                lifecycleRegistry.markState(State.DESTROYED)
            }
        })
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

}
