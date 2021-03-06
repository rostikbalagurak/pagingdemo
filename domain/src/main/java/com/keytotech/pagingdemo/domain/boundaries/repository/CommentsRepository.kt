package com.keytotech.pagingdemo.domain.boundaries.repository

import com.keytotech.pagingdemo.domain.entity.Comment
import com.keytotech.pagingdemo.domain.entity.CommentsSort
import kotlinx.coroutines.Deferred

/**
 * CommentsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface CommentsRepository {
    fun getComments(page: Int, limit: Int, sort: String = CommentsSort.LATEST.toString()): Deferred<List<Comment>>
}