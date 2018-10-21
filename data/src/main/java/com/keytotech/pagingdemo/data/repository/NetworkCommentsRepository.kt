package com.keytotech.pagingdemo.data.repository

import android.util.Log
import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import kotlinx.coroutines.Deferred

/**
 * NetworkCommentsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class NetworkCommentsRepository(private val commentsAPI: CommentsAPI) : CommentsRepository {

    override fun getComments(page: Int, limit: Int, sort: String): Deferred<List<Comment>> {
        return commentsAPI.getComments(page, limit, sort)
    }
}