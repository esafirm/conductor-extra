package extra.conductor.esafirm.com.sample

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.squareup.picasso.Picasso
import extra.conductor.esafirm.com.conductorextra.components.AbsDialogController

class DialogController : AbsDialogController() {

    @BindView(R.id.dialog_img) lateinit var imageView: ImageView

    override fun getLayoutResId(): Int = R.layout.dialog_ontroller

    override fun onViewBound(view: View) {
        Picasso.with(applicationContext)
                .load("https://unsplash.it/200/300")
                .into(imageView)
    }
}
