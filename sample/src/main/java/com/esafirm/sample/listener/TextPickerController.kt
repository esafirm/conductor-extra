package com.esafirm.sample.listener

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.listener.postEvent
import com.esafirm.conductorextra.popCurrentController

class TextPickerController : BinderController() {

    override fun getLayoutView(container: ViewGroup): View =
            LinearLayout(container.context).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(Color.WHITE)

                val editText = EditText(context).apply {
                    hint = "Insert your title here"
                    layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                addView(editText)
                addView(Button(context).apply {
                    text = "Pick"
                    layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setOnClickListener {
                        postEvent(editText.text.toString())
                        popCurrentController()
                    }
                })
            }

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
    }
}
