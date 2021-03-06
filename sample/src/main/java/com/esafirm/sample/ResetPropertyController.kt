package com.esafirm.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.common.onEvent
import com.esafirm.conductorextra.utils.resetOnDetach

class ResetPropertyController : BinderController() {

    @BindView(R.id.reset_property_txt_info) lateinit var txtInfo: TextView

    private var stringLoader: StringLoader by resetOnDetach()

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.controller_reset_property)

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        Log.d("ConductorExtra", "onViewBound $savedState")

        // Assign loader
        stringLoader = StringLoader()
        txtInfo.text = stringLoader.load()

        // Should throw error after detachment!!
        onEvent(onPostDetach = { remover ->
            txtInfo.text = try {
                stringLoader.load()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity!!, "Stringloader: $e", Toast.LENGTH_SHORT).show()
                "Destroyed"
            }
            remover()
        })
    }

    class StringLoader {
        fun load() = "Hello!!"
    }
}
