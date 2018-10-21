package com.keytotech.pagingdemo.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * ContextModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class ContextModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }
}