package com.keytotech.pagingdemo

import android.app.Activity
import android.app.Application
import com.keytotech.pagingdemo.di.AppComponent
import com.keytotech.pagingdemo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * DemoApp
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DemoApp : Application(), HasActivityInjector {

    @Inject
    internal lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: AppComponent

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun onCreate() {
        super.onCreate()
        this.initDagger()
    }

    private fun initDagger() {
        this.appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        this.appComponent
            .inject(this)
    }
}