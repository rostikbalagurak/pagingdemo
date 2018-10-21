package com.keytotech.pagingdemo.di.network

import com.google.gson.Gson
import com.keytotech.pagingdemo.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * RetrofitModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return RetrofitFactory.createApiRetrofit(
            BuildConfig.API_URL,
            gson
        )
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return Gson()
    }
}