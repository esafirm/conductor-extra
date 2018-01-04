package com.esafirm.sample.listener

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.listener.onEvent
import com.esafirm.conductorextra.pushTo

class TextReceiverController : BinderController() {

    override fun getLayoutView(container: ViewGroup): View =
            FrameLayout(container.context).apply {
                var child: ViewGroup? = null

                addView(LinearLayout(container.context).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    val textView = TextView(context).apply {
                        text = "No Title"
                        layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }

                    addView(textView)
                    addView(Button(context).apply {
                        text = "Get Title"
                        setOnClickListener {
                            getChildRouter(child!!)
                                    .pushTo(TextPickerController(), popLastView = true)
                                    .onEvent<String> {
                                        Log.d("TextView", "$textView")
                                        Log.d("Conductor bus", "Value $it")
                                        textView.text = it
                                    }
                        }
                    })
                })

                child = FrameLayout(context)
                addView(child)
            }

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {

    }

}
