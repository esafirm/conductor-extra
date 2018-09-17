package nolambda.screen

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.R
import com.esafirm.conductorextra.common.popCurrentController
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*

typealias ScreenViewProvider = ((LayoutInflater, ViewGroup) -> View)

abstract class BaseScreen : Controller, LifecycleOwner, LayoutContainer {

    override val containerView: View? get() = view

    protected lateinit var screenView: ScreenViewProvider

    /* Lifecycle */
    private val lifecycle by lazy { ControllerLifecycleRegistryOwner(this).lifecycle }

    override fun getLifecycle(): Lifecycle = lifecycle
    /* Lifecycle */

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if (::screenView.isInitialized.not()) {
            throw IllegalStateException("You haven't assign [screenView] in your Screen." +
                    " Please refer to [BaseScreen.xml] or [BaseScreen.dialog] for starter.")
        }
        return screenView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        clearFindViewByIdCache()
        super.onDestroyView(view)
    }

    /**
     * A helper and short version to use layout XML as your view
     * @return A lambda that inflate your XML res id
     */
    protected fun xml(@LayoutRes resId: Int): ScreenViewProvider = { inflater, group ->
        inflater.inflate(resId, group, false)
    }

    /**
     * A helper function to make your [Controller] view looks like a dialog
     * @return A lambda that inflate a view from [screenProvider] wrapped by dialog-like layout
     */
    protected fun dialog(screenProvider: ScreenViewProvider, handleOverlay: () -> Unit = { popCurrentController() }) =
            { inflater: LayoutInflater, group: ViewGroup ->
                inflater.inflate(R.layout.ce_controller_abs_dialog, group, false)
                        .let { it as ViewGroup }
                        .apply {
                            setOnClickListener { handleOverlay() }
                            addView(screenProvider(inflater, this))
                        }
            }
}
