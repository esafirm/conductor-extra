package com.esafirm.sample.screen

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.widget.TextView
import com.bluelinelabs.conductor.RouterTransaction
import com.esafirm.sample.R
import nolambda.screen.Screen
import nolambda.screen.ScreenViewProvider
import java.util.*

class SimpleTextScreen : Screen() {

    private val random = Random()

    override fun createView(): ScreenViewProvider = { _, group -> TextView(group.context) }

    override fun render() {
        val text = view as TextView
        text.textSize = 22f
        text.text = "${random.nextInt(10)} - $random"
        text.background = activity.attrDrawable(R.attr.selectableItemBackground)
        text.setOnClickListener {
            router.pushController(RouterTransaction.with(SimpleTextScreen()))
        }
    }

    fun Context?.attrDrawable(@AttrRes attr: Int): Drawable {
        this?.let {
            val attrs = intArrayOf(attr)
            val typedArray = it.obtainStyledAttributes(attrs)
            val drawable = typedArray.getDrawable(0)
            typedArray.recycle()
            return drawable
        }
        return ColorDrawable()
    }
}
