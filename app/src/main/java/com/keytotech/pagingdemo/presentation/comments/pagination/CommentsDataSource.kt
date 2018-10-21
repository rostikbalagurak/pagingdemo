package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.di.viewModel.Status
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import com.keytotech.pagingdemo.presentation.comments.Pagination
import kotlinx.coroutines.*
import java.util.concurrent.Executor

/**
 * CommentsDataSource
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

private const val DEBUG_SLEEP_RESPONSE_DURATION = 2000L

class CommentsDataSource(
    private val repository: CommentsRepository,
    private val pagination: Pagination,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Comment>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<Status>()

    val initialLoad = MutableLiveData<Status>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Comment>) {
        val currentPage = pagination.startPage
        val nextPage = currentPage + 1
        runBlocking {
            GlobalScope.launch(Dispatchers.Main) {
                networkState.value = Status.RUNNING
                initialLoad.value = Status.RUNNING
            }
            val response = repository.getComments(currentPage, pagination.limit).await()
            Thread.sleep(DEBUG_SLEEP_RESPONSE_DURATION)
            callback.onResult(response, null, nextPage)
            GlobalScope.launch(Dispatchers.Main) {
                networkState.value = Status.SUCCESS
                initialLoad.value = Status.SUCCESS
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
        val currentPage = params.key

        if (currentPage <= pagination.endPage) {
            val nextPage = currentPage + 1
            runBlocking {
                GlobalScope.launch(Dispatchers.Main) {
                    networkState.value = Status.RUNNING
                }
                val response = repository.getComments(currentPage, pagination.limit).await()
                Thread.sleep(DEBUG_SLEEP_RESPONSE_DURATION)
                callback.onResult(response, nextPage)
                GlobalScope.launch(Dispatchers.Main) {
                    networkState.value = Status.SUCCESS
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {

    }
}