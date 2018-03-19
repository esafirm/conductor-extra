package com.esafirm.conductorextra

import android.os.Bundle
import android.os.Parcelable
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.utils.BundleBuilder

/* --------------------------------------------------- */
/* > Props */
/* --------------------------------------------------- */

const val ARG_PROPS: String = "Argument.Props"

fun Parcelable.toPropsBundle(): Bundle = BundleBuilder().putParcelable(ARG_PROPS, this).build()

inline fun <reified T : Parcelable> Controller.getProps(): T {
    val props: T? = args.getParcelable(ARG_PROPS)
    check(props != null) { "Props must be set first with Percelable.toPropsBundle()" }
    return props!!
}

/* --------------------------------------------------- */
/* > Saved State */
/* --------------------------------------------------- */

const val ARG_SAVED_STATE: String = "Argument.SavedState"

fun Bundle.markSavedState(): Bundle = apply { putBoolean(ARG_SAVED_STATE, true) }

fun Bundle?.isMarkedSavedState(): Boolean = when (this) {
    null -> false
    else -> getBoolean(ARG_SAVED_STATE, false)
}
