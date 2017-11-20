package com.esafirm.conductorextra.components.configs

import com.bluelinelabs.conductor.Controller

typealias ConfigResolver<T> = (Class<T>) -> ControllerConfig

object ControllerConfigManager {

    private val DEFAULT_CONFIG = ControllerConfig(true)

    var configResolver: ConfigResolver<Controller> = { _ -> DEFAULT_CONFIG }
}
