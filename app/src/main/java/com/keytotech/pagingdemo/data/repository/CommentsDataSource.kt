package com.keytotech.pagingdemo.data.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.keytotech.pagingdemo.data.NetworkState
import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.data.models.Comment
import com.keytotech.pagingdemo.data.models.MockComment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor


/**
 * CommentsDataSource
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsDataSource(
    private val api: CommentsAPI,
    private val pageSize: Int = 10,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Comment>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

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
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Comment>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1
        runBlocking {
            val response = api.getComments(currentPage, pageSize).await()
            callback.onResult(response, null, nextPage)
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Comment>
    ) {

        val currentPage = params.key
        val nextPage = currentPage + 1
        runBlocking {
            val response = api.getComments(currentPage, pageSize).await()
            callback.onResult(response, nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {

    }
}