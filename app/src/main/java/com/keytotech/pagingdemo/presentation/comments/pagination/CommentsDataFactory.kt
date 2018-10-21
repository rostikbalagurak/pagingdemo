package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.CommentEntity
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * CommentsDataFactory
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsDataFactory @Inject constructor(
    private val repository: CommentsRepository,
    private val networkExecutor: Executor
) : DataSource.Factory<Int, CommentEntity>() {

    val source = MutableLiveData<CommentsDataSource>()

    override fun create(): DataSource<Int, CommentEntity> {
        val source = CommentsDataSource(
            repository = repository,
            retryExecutor = networkExecutor
        )
        this.source.postValue(source)
        return source
    }

}