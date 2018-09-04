package com.esafirm.conductorextra

import android.os.Bundle
import android.os.Parcelable
import com.bluelinelabs.conductor.Controller
import java.io.Serializable

/* --------------------------------------------------- */
/* > Props */
/* --------------------------------------------------- */

const val ARG_PROPS_TYPE = "Argument.Props.Type"
const val ARG_PROPS: String = "Argument.Props"

const val PROPS_TYPE_PARCEL = 1
const val PROPS_TYPE_SERIALIZABLE = 2

fun Parcelable.toPropsBundle(): Bundle = Bundle().apply {
    putInt(ARG_PROPS_TYPE, PROPS_TYPE_PARCEL)
    putParcelable(ARG_PROPS, this)
}

fun Serializable.toPropsBundle(): Bundle = Bundle().apply {
    putInt(ARG_PROPS_TYPE, PROPS_TYPE_SERIALIZABLE)
    putParcelable(ARG_PROPS, this)
}

inline fun <reified T : Parcelable> Controller.getProps(): T {
    val propsType = args.get(ARG_PROPS_TYPE)
    val props: T? = if (propsType == PROPS_TYPE_PARCEL) {
        args.getParcelable(ARG_PROPS)
    } else {
        args.getSerializable(ARG_PROPS).let { it as T? }
    }
    check(props != null) { "Props must be set first with Parcelable.toPropsBundle() or Serializeable.toPropsBundle()" }
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
