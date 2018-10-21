package com.keytotech.pagingdemo.di

import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * ExecutorsModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class ExecutorsModule {

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    @Provides
    fun providesNetworkExecutor(): Executor {
        return this.NETWORK_IO
    }
}