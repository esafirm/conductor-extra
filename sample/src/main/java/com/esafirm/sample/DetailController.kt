package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.utils.BundleBuilder
import com.squareup.picasso.Picasso

class DetailController(bundle: Bundle) : BinderController(bundle) {

    companion object {
        const val ARG_IMAGE = "DetailController.Image"
    }

    constructor(image: String) : this(BundleBuilder()
            .putString(ARG_IMAGE, image)
            .build())

    override fun getLayoutResId(): Int = R.layout.controller_detail

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        val imgDetail by lazy { bindingResult.findViewById(R.id.detail_img) as ImageView }
        Picasso.with(applicationContext)
                .load(args.getString(ARG_IMAGE))
                .into(imgDetail)
    }
}
