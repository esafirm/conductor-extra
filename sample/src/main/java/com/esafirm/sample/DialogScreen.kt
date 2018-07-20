package com.esafirm.sample

import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_controller.*
import nolambda.screen.Screen

class DialogScreen : Screen() {

    init {
        screenView = dialog(xml(R.layout.dialog_controller)) {
            Toast.makeText(activity, "Toast", Toast.LENGTH_LONG).show()
        }
    }

    override fun render() {
        Picasso.with(applicationContext)
                .load("https://unsplash.it/200/300")
                .into(dialog_img)
    }
}
