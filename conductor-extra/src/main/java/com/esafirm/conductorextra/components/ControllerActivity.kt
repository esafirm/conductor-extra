package com.esafirm.conductorextra.components

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

abstract class ControllerActivity : AppCompatActivity() {

    private lateinit var router: Router

    abstract fun getController(): Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }.also {
            router = Conductor.attachRouter(this@ControllerActivity, it, savedInstanceState)
                    .apply {
                        if (!hasRootController()) {
                            setRoot(RouterTransaction.with(getController()))
                        }
                    }
        })
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
