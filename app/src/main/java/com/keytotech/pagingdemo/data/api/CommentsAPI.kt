package com.keytotech.pagingdemo.data.api

import com.keytotech.pagingdemo.data.models.Comment
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * CommentsAPI
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

enum class CommentsSort(by: String) {
    LATEST("latest")
}

interface CommentsAPI {

    @GET("/comments")
    fun getComments(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String = CommentsSort.LATEST.toString()
    ): Deferred<List<Comment>>
}