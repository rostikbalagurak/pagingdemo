package com.keytotech.pagingdemo.data.di.api

import com.keytotech.pagingdemo.data.api.CommentsAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * CommentsApiModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class CommentsApiModule : ApiModuleProvider<CommentsAPI> {

    @Provides
    @Singleton
    override fun provideApi(retrofit: Retrofit): CommentsAPI {
        return retrofit.create(CommentsAPI::class.java)
    }
}