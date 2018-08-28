package com.esafirm.sample.screen

import android.arch.lifecycle.LifecycleOwner
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import com.esafirm.conductorextra.onEvent
import com.esafirm.sample.R
import io.reactivex.Single
import io.reactivex.functions.Consumer
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.controller_stateful_sample.*
import nolambda.screen.*
import nolambda.statesubject.StateSubject
import java.util.*
import java.util.concurrent.TimeUnit

class TickingValueData : StateSubject<Int>() {

    private var timer: Timer? = null
    private var value: Int = 0

    fun setInitialValue(value: Int) {
        this.value = value
    }

    override fun onActive() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                value += 1
                postValue(value)
            }
        }, 1000L, 1000L)
    }

    override fun onInactive() {
        timer?.cancel()
    }
}

@Parcelize
data class SampleState(val count: Int = 0,
                       val isLoading: Boolean = false,
                       val errorMessage: @RawValue SingleEvent<String>? = null,
                       val tickingValue: Int = 0) : Parcelable

class SamplePresenter(private val tickingValueData: TickingValueData) : Presenter<SampleState>() {

    override fun initialState() = SampleState()

    override fun bind(lifecycleOwner: LifecycleOwner) {
        tickingValueData.setInitialValue(stateSubject.value.tickingValue)
        tickingValueData.subscribe(lifecycleOwner, Consumer { value ->
            changeState {
                it.copy(tickingValue = value)
            }
        })
    }

    fun increment() = changeState {
        it.copy(count = it.count + 1)
    }

    fun decrement() = changeState {
        it.copy(count = it.count - 1)
    }

    fun reset() = changeState {
        it.copy(count = 0)
    }

    fun triggerError() = changeState {
        it.copy(errorMessage = "This is a sample error".asSingleEvent())
    }

    private fun showProgress(show: Boolean) = changeState {
        it.copy(isLoading = show)
    }

    fun asyncIncrement() = Single.timer(5, TimeUnit.SECONDS)
            .doOnSubscribe { showProgress(true) }
            .doOnDispose { showProgress(false) }
            .subscribe { _, _ -> increment() }!!
}

open class SampleStateScreen : StatefulScreen<SampleState, SamplePresenter>() {

    init {
        screenView = xml(R.layout.controller_stateful_sample)
        screenPresenter = { SamplePresenter(TickingValueData()) }
    }

    override fun render(presenter: SamplePresenter, state: SampleState) {
        txt_ticking.text = "Ticking value: ${state.tickingValue}"

        txt_yeah.text = "Counter: ${state.count}"
        txt_yeah.setOnClickListener { presenter.increment() }

        btn_reset.setOnClickListener { presenter.reset() }
        btn_add.setOnClickListener { presenter.increment() }
        btn_subtract.setOnClickListener { presenter.decrement() }
        btn_add_async.setOnClickListener { presenter.asyncIncrement() }

        progress.setOnClickListener { presenter.triggerError() }
        progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.run {
            Toast.makeText(activity?.applicationContext, this, Toast.LENGTH_SHORT).show()
        }
    }
}

class SampleLazyStateScreen : SampleStateScreen() {

    private lateinit var presenter: SamplePresenter

    init {
        screenView = xml(R.layout.controller_stateful_sample)
        screenPresenter = { presenter }

        onEvent(onPreContextAvailable = { _, remover ->
            presenter = SamplePresenter(TickingValueData())
            remover()
        })
    }
}

class SampleDiskStateSaver : SampleStateScreen() {

    private lateinit var presenter: SamplePresenter

    init {
        stateSaver = { DefaultDiskStateSaver(activity!!) }
        screenView = xml(R.layout.controller_stateful_sample)
        screenPresenter = { presenter }

        onEvent(onPreContextAvailable = { _, remover ->
            presenter = SamplePresenter(TickingValueData())
            remover()
        })
    }
}
