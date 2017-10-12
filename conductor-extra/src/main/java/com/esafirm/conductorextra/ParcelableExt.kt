package com.esafirm.conductorextra

import android.os.Bundle
import android.os.Parcelable
import com.bluelinelabs.conductor.Controller
import com.esafirm.conductorextra.utils.BundleBuilder

const val ARG_PROPS: String = "Argument.Props"

fun Parcelable.toPropsBundle(): Bundle = BundleBuilder().putParcelable(ARG_PROPS, this).build()

inline fun <reified T : Parcelable> Controller.getProps(): T = args.getParcelable(ARG_PROPS)
