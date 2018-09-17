package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.common.getProps
import com.esafirm.conductorextra.common.toPropsBundle
import com.squareup.picasso.Picasso

class DetailWithPropsController(bundle: Bundle) : BinderController(bundle) {

    constructor(props: DetailProps) : this(props.toPropsBundle())
    constructor(props: DetailPropsSerializeable) : this(props.toPropsBundle())

    @BindView(R.id.detail_img) lateinit var imageView: ImageView
    @BindView(R.id.detail_txt) lateinit var txtDetail: TextView

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.controller_detail_with_props)

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        val props = try {
            getProps<DetailProps>()
        } catch (e: Exception) {
            getProps<DetailPropsSerializeable>()
        }

        val (image, caption) = when (props) {
            is DetailProps -> props.image to props.caption
            is DetailPropsSerializeable -> props.image to props.caption
            else -> throw  IllegalArgumentException("Props not supported")
        }

        Picasso.with(applicationContext)
                .load(image)
                .into(imageView)

        txtDetail.text = caption
    }
}
