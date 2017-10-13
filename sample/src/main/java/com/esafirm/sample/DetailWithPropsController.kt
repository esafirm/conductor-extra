package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.getProps
import com.esafirm.conductorextra.toPropsBundle
import com.squareup.picasso.Picasso

class DetailWithPropsController(bundle: Bundle) : BinderController(bundle) {

    constructor(props: DetailProps) : this(props.toPropsBundle())

    @BindView(R.id.detail_img) lateinit var imageView: ImageView
    @BindView(R.id.detail_txt) lateinit var txtDetail: TextView

    override fun getLayoutResId(): Int = R.layout.controller_detail_with_props

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        val props = getProps<DetailProps>()

        Picasso.with(applicationContext)
                .load(props.image)
                .into(imageView)

        txtDetail.text = props.caption
    }
}
