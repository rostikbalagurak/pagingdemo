package com.keytotech.pagingdemo.data.di.api

import retrofit2.Retrofit

/**
 * ApiModule
 *
 * Retrofit API provider
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface ApiModuleProvider<T> {

    /**
     * Provides API
     * @param retrofit - Retrofit to build API with
     *
     * @return An implementation of the API
     */
    fun provideApi(retrofit: Retrofit): T
}