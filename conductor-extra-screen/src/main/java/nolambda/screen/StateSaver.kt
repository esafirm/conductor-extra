package nolambda.screen

import android.os.Bundle

interface StateSaver<State> {

    companion object {
        const val KEY_STATE = "SCREEN_STATE"
    }

    fun onSaveInstanceState(state: State, outState: Bundle)
    fun onRestoreInstanceState(savedInstanceState: Bundle): State
}
