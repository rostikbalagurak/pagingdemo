package com.keytotech.pagingdemo.data.di.repository

import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.data.repository.NetworkCommentsRepository
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCommentsRepository(commentsAPI: CommentsAPI): CommentsRepository {
        return NetworkCommentsRepository(commentsAPI)
    }
}