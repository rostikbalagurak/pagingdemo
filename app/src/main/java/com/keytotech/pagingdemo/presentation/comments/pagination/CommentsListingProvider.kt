package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import com.keytotech.pagingdemo.presentation.comments.Pagination
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * CommentsListingProvider
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsListingProvider @Inject constructor(
    private val commentsAPI: CommentsRepository,
    private val networkExecutor: Executor
) {

    @MainThread
    fun comments(pagination: Pagination): CommentListing<Comment> {
        val dataFactory = CommentsDataFactory(commentsAPI, networkExecutor, pagination)
        val config = pagedListConfig(pagination)

        val livePagedList = LivePagedListBuilder(dataFactory, config)
            .setFetchExecutor(networkExecutor)
            .build()
        val refreshState = Transformations.switchMap(dataFactory.source) {
            it.initialLoad
        }
        return CommentListing(
            pagedList = livePagedList,
            networkResource = Transformations.switchMap(dataFactory.source) {
                it.networkState
            },
            retry = {
                dataFactory.source.value?.retryAllFailed()
            },
            refresh = {
                dataFactory.source.value?.invalidate()
            },
            refreshResource = refreshState
        )
    }

    private fun pagedListConfig(pagination: Pagination): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pagination.limit * 2)
            .setPageSize(pagination.limit)
            .build()
    }

}
