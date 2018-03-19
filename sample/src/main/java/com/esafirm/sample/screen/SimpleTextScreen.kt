package com.esafirm.sample.screen

import android.widget.TextView
import nolambda.screen.Screen

class SimpleTextScreen : Screen() {

    init {
        screenView = { _, group -> TextView(group.context) }
    }

    override fun render() {
        val text = view as TextView
        text.text = "This is a simple Text"
    }
}
