package com.keytotech.pagingdemo.data.di

import com.keytotech.pagingdemo.data.di.api.ApiModule
import com.keytotech.pagingdemo.data.di.api.ExecutorsModule
import com.keytotech.pagingdemo.data.di.repository.RepositoryModule
import com.keytotech.pagingdemo.data.retrofit.RetrofitModule
import dagger.Module

/**
 * DataModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module(
    includes = [
        ApiModule::class,
        RepositoryModule::class,
        ExecutorsModule::class,
        RetrofitModule::class
    ]
)
class DataModule