package com.conductor.esafirm.com.sample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.conductor.esafirm.com.conductorextra.components.AbsController
import com.conductor.esafirm.com.conductorextra.utils.BundleBuilder

class DetailController(bundle: Bundle) : AbsController(bundle) {

    companion object {
        const val ARG_IMAGE = "DetailController.Image"
    }

    constructor(image: String) : this(BundleBuilder()
            .putString(ARG_IMAGE, image)
            .build())

    override fun getLayoutResId(): Int = R.layout.controller_detail

    override fun onViewBound(view: View) {
        val imgDetail by lazy { view.findViewById(R.id.detail_img) as ImageView }
        Picasso.with(applicationContext)
                .load(args.getString(ARG_IMAGE))
                .into(imgDetail)
    }
}