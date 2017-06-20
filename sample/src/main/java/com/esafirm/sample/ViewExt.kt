package com.esafirm.sample

import android.view.View
import android.view.ViewGroup

fun ViewGroup.applyRecursively(apply: (View) -> Unit) {
    0.rangeTo(childCount - 1).forEach {
        val child = getChildAt(it)
        when (child) {
            is ViewGroup -> child.applyRecursively(apply)
            else -> apply(child)
        }
    }
}

