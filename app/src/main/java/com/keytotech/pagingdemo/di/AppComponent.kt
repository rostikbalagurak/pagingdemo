package com.keytotech.pagingdemo.di

import android.app.Application
import com.keytotech.pagingdemo.DemoApp
import com.keytotech.pagingdemo.data.di.DataModule
import com.keytotech.pagingdemo.di.viewModel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * AppComponent
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        DataModule::class,
        ActivityBuilder::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


    fun inject(app: DemoApp)
}