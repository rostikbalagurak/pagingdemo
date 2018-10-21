package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.domain.boundaries.repository.CommentsRepository
import com.keytotech.pagingdemo.domain.entity.Comment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

/**
 * CommentsDataSource
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsDataSource(
    private val repository: CommentsRepository,
    private val pageSize: Int = 10,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Comment>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkResource<*>>()

    val initialLoad = MutableLiveData<NetworkResource<*>>()

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
        val currentPage = 1
        val nextPage = currentPage + 1
        runBlocking {
            val response = repository.getComments(currentPage, pageSize).await()
            callback.onResult(response, null, nextPage)
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
        val currentPage = params.key
        val nextPage = currentPage + 1
        runBlocking {
            val response = repository.getComments(currentPage, pageSize).await()
            callback.onResult(response, nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {

    }
}