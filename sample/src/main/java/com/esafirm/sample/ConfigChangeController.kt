package com.esafirm.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.esafirm.conductorextra.onEvent
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.sample.utils.Counter
import java.util.*

class ConfigChangeController : BinderController() {

    @BindView(R.id.fetching_txt_main) lateinit var txtMain: TextView

    private val counter = Counter().also { Log.w("Counter", "Initialize counter") }
    private val timer = Timer()

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.controller_config_change)

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        txtMain.text = counter.count.toString()

        Logger.log("TextView: $txtMain")

        if (savedState == null) {
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    counter.increment()
                    activity?.runOnUiThread {
                        txtMain.text = counter.count.toString()
                    }
                }
            }, 1000, 1000)

            onEvent(onPreDestroy = { _, remover ->
                timer.cancel()
                remover()
            })
        }
    }
}
