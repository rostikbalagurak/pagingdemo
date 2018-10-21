package com.keytotech.pagingdemo.data.repository

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import com.keytotech.pagingdemo.data.Listing
import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.data.models.Comment
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * CommentsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsRepository @Inject constructor(
    private val commentsAPI: CommentsAPI,
    private val networkExecutor: Executor
) {

    private val dataFactory = CommentsDataFactory(commentsAPI, networkExecutor)

    @MainThread
    fun comments(pageSize: Int): Listing<Comment> {
        val config = pagedListConfig(pageSize)
        val livePagedList = LivePagedListBuilder(dataFactory, config)
            .setFetchExecutor(networkExecutor)
            .build()
        val refreshState = Transformations.switchMap(dataFactory.source) {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(dataFactory.source) {
                it.networkState
            },
            retry = {
                dataFactory.source.value?.retryAllFailed()
            },
            refresh = {
                dataFactory.source.value?.invalidate()
            },
            refreshState = refreshState
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
