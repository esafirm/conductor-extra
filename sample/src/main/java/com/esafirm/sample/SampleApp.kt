package com.esafirm.sample

import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

class SampleApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        Stetho.initializeWithDefaults(this)
    }
}
