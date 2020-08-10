package com.esafirm.sample

import android.util.Log

object Logger {

    private const val TAG = "Conductor Extra"

    fun log(message: String) {
        Log.d(TAG, message)
    }
}
