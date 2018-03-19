package nolambda.screen

import android.os.Bundle
import com.esafirm.conductorextra.addLifecycleCallback

abstract class Screen : BaseScreen {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            render()
            remover()
        })
    }

    abstract fun render()
}
