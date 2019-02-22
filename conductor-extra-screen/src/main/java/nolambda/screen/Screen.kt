package nolambda.screen

import android.os.Bundle
import com.esafirm.conductorextra.common.onEvent

abstract class Screen : BaseScreen {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    init {
        onEvent(
                onPostAttach = { render() },
                onPostCreateView = { initView() }
        )
    }

    /** This function called after [onCreateView] **/
    protected fun initView() {
        // NO-OP
    }

    abstract fun render()
}
