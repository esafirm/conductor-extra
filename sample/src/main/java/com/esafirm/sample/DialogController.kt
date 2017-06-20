package com.esafirm.sample

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.AbsDialogController
import com.squareup.picasso.Picasso

class DialogController : AbsDialogController() {

    @BindView(R.id.dialog_img) lateinit var imageView: ImageView

    override fun getLayoutResId(): Int = R.layout.dialog_ontroller

    override fun onViewBound(bindingResult: View) {
        Picasso.with(applicationContext)
                .load("https://unsplash.it/200/300")
                .into(imageView)
    }
}
