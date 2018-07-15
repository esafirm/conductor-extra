package nolambda.screen

import android.os.Bundle
import android.os.Parcelable

class DefaultStateSaver<State> : StateSaver<State> {

    override fun onSaveInstanceState(state: State, outState: Bundle) {
        if (state != null && state is Parcelable) {
            outState.putParcelable(StateSaver.KEY_STATE, state)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle): State {
        @Suppress("UNCHECKED_CAST")
        return savedInstanceState.getParcelable<Parcelable>(StateSaver.KEY_STATE) as State
    }
}
