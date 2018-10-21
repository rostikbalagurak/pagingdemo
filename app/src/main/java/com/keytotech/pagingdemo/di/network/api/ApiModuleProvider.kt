package com.keytotech.pagingdemo.di.network.api

import dagger.Module
import retrofit2.Retrofit

/**
 * ApiModule
 *
 * Retrofit API provider
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
interface ApiModuleProvider<T> {

    /**
     * Provides API
     * @param retrofit - Retrofit to build API with
     *
     * @return An implementation of the API
     */
    fun provideApi(retrofit: Retrofit): T
}