package nolambda.screen

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*

abstract class BaseScreen : Controller, LifecycleOwner, LayoutContainer {

    override val containerView: View? get() = view

    protected lateinit var screenView: ((LayoutInflater, ViewGroup) -> View)

    /* Lifecycle */
    private val lifecycle by lazy { ControllerLifecycleRegistryOwner(this).lifecycle }

    override fun getLifecycle(): Lifecycle = lifecycle
    /* Lifecycle */

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return screenView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        clearFindViewByIdCache()
        super.onDestroyView(view)
    }

    protected fun xml(@LayoutRes resId: Int) = { inflater: LayoutInflater, group: ViewGroup ->
        inflater.inflate(resId, group, false)
    }
}
