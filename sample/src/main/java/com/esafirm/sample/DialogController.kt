package com.esafirm.sample

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import com.esafirm.conductorextra.butterknife.BinderDialogController
import com.squareup.picasso.Picasso

class DialogController : BinderDialogController() {

    @BindView(R.id.dialog_img) lateinit var imageView: ImageView

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.dialog_ontroller)

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        Picasso.with(applicationContext)
                .load("https://unsplash.it/200/300")
                .into(imageView)
    }
}
