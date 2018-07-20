package com.esafirm.sample

import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary

class SampleApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
