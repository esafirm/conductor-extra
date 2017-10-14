package com.esafirm.sample

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.showDialog
import com.esafirm.conductorextra.transaction.Routes
import com.squareup.picasso.Picasso
import java.util.*

class MainController : BinderController() {

    @BindView(R.id.main_container_dialog) lateinit var container: ViewGroup

    init {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }

    override fun getLayoutResId(): Int = R.layout.controller_main

    override fun onViewBound(bindingResult: View, savedState: Bundle?) {
        val images = listOf(
                "https://unsplash.it/200/300",
                "https://unsplash.it/300/300",
                "https://unsplash.it/400/500"
        )

        val random by lazy { Random() }
        val viewGroup = bindingResult as ViewGroup

        viewGroup.applyRecursively {
            when (it) {
                is ImageView -> it.apply {

                    val selectedImage = images[random.nextInt(images.size)]

                    scaleType = ImageView.ScaleType.CENTER_CROP
                    Picasso.with(applicationContext)
                            .load(selectedImage)
                            .into(it)

                    setOnClickListener {
                        val withProps = random.nextBoolean()
                        if (withProps) {
                            router.pushController(Routes.simpleTransaction(
                                    DetailWithPropsController(DetailProps("https://unsplash.it/200/300", "Hello world!")),
                                    VerticalChangeHandler()
                            ))
                        } else {
                            router.pushController(Routes.simpleTransaction(
                                    DetailController(selectedImage),
                                    VerticalChangeHandler()
                            ))
                        }
                        Toast.makeText(
                                applicationContext,
                                "Show with props: $withProps",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    post {
                        ViewCompat.setAlpha(this, 0f)
                        ViewCompat.setTranslationY(this, 20f)
                        ViewCompat.animate(this)
                                .setStartDelay(Math.max(800, (Math.random() * 1800).toLong()))
                                .alpha(1f)
                                .translationY(0f)
                    }
                }
            }
        }
    }

    @OnClick(R.id.main_btn_reset_property)
    fun onShowResetProperty() {
        router.pushController(Routes.simpleTransaction(
                ResetPropertyController(),
                VerticalChangeHandler()
        ))
    }

    @OnClick(R.id.main_btn_show_fetching)
    fun onShowFetching() {
        router.pushController(Routes.simpleTransaction(
                ConfigChangeController(),
                VerticalChangeHandler()
        ))
    }

    @OnClick(R.id.main_btn_show_dialog)
    fun onShowDialogClick() {
        showDialog(getChildRouter(container), DialogController())
    }

    @OnClick(R.id.main_btn_show_data_binding)
    fun onShowDataBinding() {
        router.pushController(Routes.simpleTransaction(
                DetailControllerDataBinding(),
                VerticalChangeHandler()
        ))
    }
}
