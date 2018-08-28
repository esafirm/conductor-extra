package nolambda.screen

import android.os.Bundle
import com.esafirm.conductorextra.onEvent

abstract class Screen : BaseScreen {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    init {
        onEvent(onPostAttach = { _, _, _ -> render() })
    }

    abstract fun render()
}
