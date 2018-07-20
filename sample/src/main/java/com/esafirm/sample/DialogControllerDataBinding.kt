package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.esafirm.conductorextra.databinding.BinderDialogController
import com.esafirm.sample.databinding.DialogOntrollerBindingBinding
import com.squareup.picasso.Picasso

class DialogControllerDataBinding : BinderDialogController<DialogOntrollerBindingBinding>() {

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.dialog_ontroller_binding)

    override fun onViewBound(bindingResult: DialogOntrollerBindingBinding, savedState: Bundle?) {
        Picasso.with(applicationContext)
                .load("https://unsplash.it/200/300")
                .into(bindingResult.dialogImg)
    }
}
