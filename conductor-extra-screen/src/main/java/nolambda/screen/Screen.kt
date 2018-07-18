package nolambda.screen

import android.os.Bundle
import com.esafirm.conductorextra.addLifecycleCallback

abstract class Screen : BaseScreen {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    init {
        addLifecycleCallback(onPostAttach = { _, _, _ -> render() })
    }

    abstract fun render()
}
