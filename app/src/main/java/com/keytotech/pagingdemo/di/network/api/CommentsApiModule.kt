package com.keytotech.pagingdemo.di.network.api

import com.keytotech.pagingdemo.data.api.CommentsAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * CommentsApiModule
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Module
class CommentsApiModule : ApiModuleProvider<CommentsAPI> {

    @Provides
    override fun provideApi(retrofit: Retrofit): CommentsAPI {
        return retrofit.create(CommentsAPI::class.java)
    }
}