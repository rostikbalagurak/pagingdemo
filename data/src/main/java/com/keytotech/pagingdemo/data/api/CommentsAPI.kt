package com.keytotech.pagingdemo.data.api

import com.keytotech.pagingdemo.domain.entity.CommentEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * CommentsAPI
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

interface CommentsAPI {

    @GET("/comments")
    fun getComments(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): Deferred<List<CommentEntity>>
}