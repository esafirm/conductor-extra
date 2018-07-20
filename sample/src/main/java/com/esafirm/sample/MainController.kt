package com.esafirm.sample

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import com.esafirm.conductorextra.butterknife.BinderController
import com.esafirm.conductorextra.pushTo
import com.esafirm.conductorextra.showDialog
import com.esafirm.conductorextra.transaction.Routes
import com.esafirm.sample.listener.TextReceiverController
import com.esafirm.sample.ovb.OnViewBoundTestController
import com.esafirm.sample.screen.SampleLazyStateScreen
import com.esafirm.sample.screen.SampleStateScreen
import com.esafirm.sample.screen.SimpleTextScreen
import com.squareup.picasso.Picasso
import java.util.*

class MainController : BinderController() {

    @BindView(R.id.main_container_dialog) lateinit var container: ViewGroup

    init {
        retainViewMode = RetainViewMode.RETAIN_DETACH
        setHasOptionsMenu(true)
    }

    override fun getLayoutView(container: ViewGroup): View =
            container.inflate(R.layout.controller_main)

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
                        alpha = 0f
                        translationY = 20f
                        ViewCompat.animate(this)
                                .setStartDelay(Math.max(800, (Math.random() * 1800).toLong()))
                                .alpha(1f)
                                .translationY(0f)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reset_property -> onShowResetProperty()
            R.id.menu_config_change -> onShowConfigChanges()
            R.id.menu_show_data_binding -> onShowDataBinding()
            R.id.menu_show_dialog -> showDialog(getChildRouter(container), DialogController())
            R.id.menu_experiment_ovb -> router.pushTo(OnViewBoundTestController())
            R.id.menu_experiment_listener -> router.pushTo(TextReceiverController())
            R.id.menu_experiment_screen_simple -> router.pushTo(SimpleTextScreen())
            R.id.menu_experiment_screen_stateful -> router.pushTo(SampleStateScreen())
            R.id.menu_experiment_screen_stateful_lazy -> router.pushTo(SampleLazyStateScreen())
        }
        return true
    }

    private fun onShowResetProperty() = router.pushTo(ResetPropertyController())

    private fun onShowConfigChanges() = router.pushTo(ConfigChangeController())

    private fun onShowDataBinding() = router.pushTo(DetailControllerDataBinding())
}
