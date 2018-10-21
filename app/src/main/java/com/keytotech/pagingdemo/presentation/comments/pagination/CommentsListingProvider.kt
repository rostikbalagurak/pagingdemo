package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * CommentsListingProvider
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsListingProvider @Inject constructor(
    commentsAPI: CommentsRepository,
    private val networkExecutor: Executor
) {

    private val dataFactory =
        CommentsDataFactory(commentsAPI, networkExecutor)

    @MainThread
    fun comments(pageSize: Int): CommentListing<Comment> {
        val config = pagedListConfig(pageSize)
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

    private fun pagedListConfig(pageSize: Int): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPageSize(pageSize)
            .build()
    }

}
