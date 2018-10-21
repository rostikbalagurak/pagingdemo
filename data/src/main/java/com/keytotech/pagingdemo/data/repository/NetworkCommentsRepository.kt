package com.keytotech.pagingdemo.data.repository

import android.util.Log
import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import kotlinx.coroutines.Deferred

class NetworkCommentsRepository(private val commentsAPI: CommentsAPI) : CommentsRepository {

    override fun getComments(page: Int, limit: Int, sort: String): Deferred<List<Comment>> {
        Log.d("AAAA", "Loading page: $page, limit: $limit")
        return commentsAPI.getComments(page, limit, sort)
    }
}