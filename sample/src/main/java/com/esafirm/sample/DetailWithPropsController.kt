package com.conductor.esafirm.com.sample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.squareup.picasso.Picasso
import com.conductor.esafirm.com.conductorextra.components.AbsController
import com.conductor.esafirm.com.conductorextra.getProps
import com.conductor.esafirm.com.conductorextra.toBundle

class DetailWithPropsController(bundle: Bundle) : AbsController(bundle) {

    constructor(props: DetailProps) : this(props.toBundle())

    @BindView(R.id.detail_img) lateinit var imageView: ImageView
    @BindView(R.id.detail_txt) lateinit var txtDetail: TextView

    override fun getLayoutResId(): Int = R.layout.controller_detail_with_props

    override fun onViewBound(view: View) {
        val props = getProps<DetailProps>()

        Picasso.with(applicationContext)
                .load(props.image)
                .into(imageView)

        txtDetail.text = props.caption
    }
}
