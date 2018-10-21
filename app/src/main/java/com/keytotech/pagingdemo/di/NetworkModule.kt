package com.keytotech.pagingdemo.di

import com.keytotech.pagingdemo.di.network.RetrofitModule
import com.keytotech.pagingdemo.di.network.api.CommentsApiModule
import dagger.Module

/**
 * NetworkModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module(
    includes = [
        AppModule::class,
        RetrofitModule::class,
        CommentsApiModule::class
    ]
)
class NetworkModule