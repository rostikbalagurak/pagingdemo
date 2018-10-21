package com.keytotech.pagingdemo.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * AppModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }
}