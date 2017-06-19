package com.conductor.esafirm.com.conductorextra.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller

abstract class AbsController : Controller {

    lateinit var unbinder: Unbinder

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    constructor()
    constructor(bundle: Bundle) : super(bundle)

    /* --------------------------------------------------- */
    /* > To be overrode */
    /* --------------------------------------------------- */

    abstract fun getLayoutResId(): Int
    abstract fun onViewBound(view: View)

    fun onSetupComponent() {}

    /* --------------------------------------------------- */
    /* > Lifecycle */
    /* --------------------------------------------------- */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        onSetupComponent()
        return inflater.inflate(getLayoutResId(), container, false)
                .also {
                    postCreateView(it)
                }
    }

    protected fun postCreateView(view: View) {
        unbinder = ButterKnife.bind(this@AbsController, view)
        onViewBound(view)
    }

    override fun onDestroyView(view: View) {
        unbinder.unbind()
        super.onDestroyView(view)
    }
}
