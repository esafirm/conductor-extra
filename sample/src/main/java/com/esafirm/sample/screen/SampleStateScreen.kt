package com.esafirm.sample.screen

import android.arch.lifecycle.LifecycleOwner
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import com.esafirm.conductorextra.common.onEvent
import com.esafirm.conductorextra.common.pushTo
import com.esafirm.conductorextra.listener.listen
import com.esafirm.sample.Logger
import com.esafirm.sample.R
import com.esafirm.sample.listener.TextPickerController
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
        }, 0L, 1000L)
    }

    override fun onInactive() {
        timer?.cancel()
    }
}

@Parcelize
data class SampleState(val count: Int = 0,
                       val isLoading: Boolean = false,
                       val errorMessage: @RawValue SingleEvent<String>? = null,
                       val tickingValue: Int = 0,
                       val tickingValueCaption: String = "Ticking Value: ") : Parcelable

class SamplePresenter(private val tickingValueData: TickingValueData) : Presenter<SampleState>() {

    override fun initialState() = SampleState()

    override fun bind(lifecycleOwner: LifecycleOwner) {
        tickingValueData.setInitialValue(stateSubject.value.tickingValue)
        tickingValueData.subscribe(lifecycleOwner, Consumer { value ->
            setState {
                it.copy(tickingValue = value)
            }
        })
    }

    fun increment() = setState {
        it.copy(count = it.count + 1)
    }

    fun decrement() = setState {
        it.copy(count = it.count - 1)
    }

    fun reset() = setState {
        it.copy(count = 0)
    }

    fun triggerError() = setState {
        it.copy(errorMessage = "This is a sample error".asSingleEvent())
    }

    private fun showProgress(show: Boolean) = setState {
        it.copy(isLoading = show)
    }

    fun syncIncrement() = setState {
        Thread.sleep(2000)
        it.copy(count = it.count + 1)
    }

    fun asyncIncrement() {
        Single.timer(5, TimeUnit.SECONDS)
                .doOnSubscribe { showProgress(true) }
                .doFinally { showProgress(false) }
                .doOnDispose { Logger.log("onDispose") }
                .subscribe { _, _ -> increment() }
    }

    fun setTickingValueCaption(caption: String) = setState {
        it.copy(tickingValueCaption = caption)
    }
}

open class SampleStateScreen : StatefulScreen<SampleState, SamplePresenter>() {

    override fun createView() = xml(R.layout.controller_stateful_sample)
    override fun createPresenter() = SamplePresenter(TickingValueData())

    private fun renderPicker(presenter: SamplePresenter, state: SampleState) {
        txt_ticking.text = "${state.tickingValueCaption}${state.tickingValue}"
        txt_ticking.setOnClickListener {
            router.pushTo(TextPickerController().apply {
                listen<String> {
                    presenter.setTickingValueCaption(it)
                }
            })
        }
    }

    override fun render(presenter: SamplePresenter, state: SampleState) {
        renderPicker(presenter, state)

        txt_yeah.text = "Counter: ${state.count}"
        txt_yeah.setOnClickListener { presenter.increment() }

        btn_reset.setOnClickListener { presenter.reset() }
        btn_add.setOnClickListener { presenter.increment() }
        btn_subtract.setOnClickListener { presenter.decrement() }
        btn_add_async.setOnClickListener { presenter.asyncIncrement() }
        btn_add_sync.setOnClickListener { presenter.syncIncrement() }

        progress.setOnClickListener { presenter.triggerError() }
        progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.fetch {
            Toast.makeText(activity?.applicationContext, this, Toast.LENGTH_SHORT).show()
        }
    }
}

class SampleLazyStateScreen : SampleStateScreen() {

    private lateinit var presenter: SamplePresenter

    override fun createView() = xml(R.layout.controller_stateful_sample)
    override fun createPresenter() = presenter

    init {
        onEvent(onPreContextAvailable = { remover ->
            presenter = SamplePresenter(TickingValueData())
            remover()
        })
    }
}

class SampleDiskStateSaver : SampleStateScreen() {

    private lateinit var presenter: SamplePresenter

    init {
        stateSaver = { DefaultDiskStateSaver(activity!!) }
        onEvent(onPreContextAvailable = { remover ->
            presenter = SamplePresenter(TickingValueData())
            remover()
        })
    }

    override fun createPresenter() = presenter
    override fun createView() = xml(R.layout.controller_stateful_sample)
}
