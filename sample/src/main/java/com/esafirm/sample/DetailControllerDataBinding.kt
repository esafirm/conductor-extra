package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.esafirm.conductorextra.databinding.BinderController
import com.esafirm.sample.databinding.ControllerDataBindingBinding

class DetailControllerDataBinding : BinderController<ControllerDataBindingBinding>() {

    override fun onViewBound(bindingResult: ControllerDataBindingBinding, savedState: Bundle?) {
        bindingResult.detailImg.setImageResource(R.mipmap.ic_launcher)
    }

    override fun getLayoutView(container: ViewGroup): View = container.inflate(R.layout.controller_data_binding)
}
