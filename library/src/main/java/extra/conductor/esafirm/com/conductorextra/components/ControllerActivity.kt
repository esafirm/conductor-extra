package extra.conductor.esafirm.com.conductorextra.components

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class ControllerActivity : AppCompatActivity() {

    var unbinder: Unbinder? = null

    abstract fun getLayoutResId(): Int
    abstract fun onViewBound(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        unbinder = ButterKnife.bind(this)
        onViewBound(savedInstanceState)
    }

    override fun onDestroy() {
        unbinder?.unbind()
        super.onDestroy()
    }
}
