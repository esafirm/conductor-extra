package nolambda.screen

import android.os.Bundle
import android.os.Parcelable

class DefaultStateSaver<STATE> : StateSaver<STATE> {

    override fun onSaveInstanceState(state: STATE, outState: Bundle) {
        if (state != null && state is Parcelable) {
            outState.putParcelable(StateSaver.KEY_STATE, state)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle): STATE {
        @Suppress("UNCHECKED_CAST")
        return savedInstanceState.getParcelable<Parcelable>(StateSaver.KEY_STATE) as STATE
    }
}
