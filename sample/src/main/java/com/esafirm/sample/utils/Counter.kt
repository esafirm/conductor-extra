package com.esafirm.sample.utils

class Counter {
    var count: Int = 0
    fun increment() = (count++).let { count }
}
