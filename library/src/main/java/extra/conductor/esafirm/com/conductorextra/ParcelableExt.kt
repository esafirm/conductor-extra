package extra.conductor.esafirm.com.conductorextra

import android.os.Bundle
import android.os.Parcelable
import com.bluelinelabs.conductor.Controller
import extra.conductor.esafirm.com.conductorextra.utils.BundleBuilder

const val ARG_PROPS: String = "Argument.Props"

fun Parcelable.toBundle(): Bundle = BundleBuilder().putParcelable(ARG_PROPS, this).build()

inline fun <reified T : Parcelable> Controller.getProps(): T = args.getParcelable<T>(ARG_PROPS)
