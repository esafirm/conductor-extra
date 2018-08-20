package nolambda.screen

import android.content.Context
import android.os.Bundle
import com.orhanobut.hawk.Hawk
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*

class DefaultDiskStateSaver<STATE>(context: Context) : StateSaver<STATE> {

    companion object {
        private const val SESSION_KEY = "_DDSS_Session"
        private const val SESSION_LIST_KEY = "_DDSS_SList"
    }

    init {
        HawkInitializer.init(context)
    }

    private val sessionId by lazy { UUID.randomUUID().toString() }

    override fun onSaveInstanceState(state: STATE, outState: Bundle) {
        outState.putString(SESSION_KEY, sessionId)
        saveState(state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle): STATE {
        val sessionId = savedInstanceState.getString(SESSION_KEY)
        val state = Hawk.get<STATE>(sessionId)

        deleteUnusedStates()

        return state
    }

    private fun saveState(state: STATE) {
        Hawk.put(sessionId, state)
        Hawk.put(SESSION_LIST_KEY, getCurrentSessionList() + listOf(sessionId))
    }

    private fun deleteUnusedStates() = Completable.fromAction {
        getCurrentSessionList().forEach {
            Hawk.delete(it)
        }
    }.subscribeOn(Schedulers.io())
            .onErrorComplete()
            .subscribe()

    private fun getCurrentSessionList() = Hawk.get<List<String>>(SESSION_LIST_KEY, emptyList())
}
