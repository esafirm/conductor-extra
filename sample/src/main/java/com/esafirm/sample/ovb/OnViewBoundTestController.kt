package com.esafirm.sample.ovb

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.pushTo
import com.esafirm.sample.Logger
import com.esafirm.sample.R
import com.esafirm.sample.utils.Counter

class OnViewBoundTestController : BinderController() {

    @BindView(R.id.btn_add_stack) lateinit var btnAddStack: View
    @BindView(R.id.text) lateinit var text: TextView
    @BindView(R.id.switch_retain_view) lateinit var retainSwitch: SwitchCompat

    private val counter = Counter()

    override fun getLayoutView(container: ViewGroup) = container.inflate(R.layout.controller_experiment_ovb)

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        Logger.log("onViewBoundCalled in $this")

        text.textSize = 28f
        text.setTextColor(Color.BLACK)
        text.text = counter.increment().toString()

        btnAddStack.setOnClickListener {
            router.pushTo(OnViewBoundTestController())
        }

        retainSwitch.setOnCheckedChangeListener { _, isCheked ->
            retainViewMode = when (isCheked) {
                true -> RetainViewMode.RETAIN_DETACH
                false -> RetainViewMode.RELEASE_DETACH
            }
        }
    }


}
