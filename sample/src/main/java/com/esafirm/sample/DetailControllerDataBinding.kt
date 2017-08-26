package com.esafirm.sample

import com.esafirm.conductorextra.databinding.BinderController
import com.esafirm.sample.databinding.ControllerDataBindingBinding

class DetailControllerDataBinding : BinderController<ControllerDataBindingBinding>() {

    override fun onViewBound(bindingResult: ControllerDataBindingBinding) {
        bindingResult.detailImg.setImageResource(R.mipmap.ic_launcher)
    }

    override fun getLayoutResId(): Int = R.layout.controller_data_binding
}
