package com.esafirm.sample.screen

import android.os.Parcelable
import android.view.View
import android.widget.Toast
import com.esafirm.sample.R
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.controller_stateful_sample.*
import nolambda.screen.Presenter
import nolambda.screen.SingleEvent
import nolambda.screen.StatefulScreen
import nolambda.screen.asSingleEvent
import nolambda.statesubject.StateSubject
import java.util.concurrent.TimeUnit

@Parcelize
data class SampleState(val count: Int = 0,
                       val isLoading: Boolean = false,
                       val errorMessage: @RawValue SingleEvent<String>? = null) : Parcelable

class SamplePresenter : Presenter<SampleState>() {

    override val stateSubject = StateSubject<SampleState>().apply { postValue(SampleState()) }

    fun increment() = changeState {
        it.copy(count = it.count + 1, isLoading = false)
    }

    fun reset() = changeState {
        it.copy(count = 0)
    }

    fun triggerError() = changeState {
        it.copy(errorMessage = "This is a sample error".asSingleEvent())
    }

    private fun showProgress() = changeState {
        it.copy(isLoading = true)
    }

    fun asyncIncrement() = Single.timer(5, TimeUnit.SECONDS)
            .doOnSubscribe { showProgress() }
            .subscribe { _, _ -> increment() }!!
}

class SampleStateScreen : StatefulScreen<SampleState>() {

    init {
        screenView = xml(R.layout.controller_stateful_sample)
        screenPresenter = SamplePresenter()
    }

    override fun render(state: SampleState) {
        val presenter = screenPresenter as SamplePresenter

        txt_yeah.text = "Counter: ${state.count}"
        txt_yeah.setOnClickListener { presenter.increment() }

        btn_reset.setOnClickListener { presenter.reset() }
        btn_add_async.setOnClickListener { presenter.asyncIncrement() }

        progress.setOnClickListener { presenter.triggerError() }
        progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.errorMessage?.run {
            Toast.makeText(activity?.applicationContext, this, Toast.LENGTH_SHORT).show()
        }
    }
}
