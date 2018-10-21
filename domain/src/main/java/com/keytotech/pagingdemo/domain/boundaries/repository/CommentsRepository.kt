package com.keytotech.pagingdemo.domain.boundaries.repository

import com.keytotech.pagingdemo.domain.entity.CommentEntity
import com.keytotech.pagingdemo.domain.entity.CommentsSort
import kotlinx.coroutines.Deferred

interface CommentsRepository {
    fun getComments(page: Int, limit: Int, sort: String = CommentsSort.LATEST.toString()): Deferred<List<CommentEntity>>
}