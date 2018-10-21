package com.keytotech.pagingdemo.data.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.keytotech.pagingdemo.data.api.CommentsAPI
import com.keytotech.pagingdemo.data.models.Comment
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * CommentsDataFactory
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsDataFactory @Inject constructor(
    private val api: CommentsAPI,
    private val networkExecutor: Executor
) : DataSource.Factory<Int, Comment>() {

    val source = MutableLiveData<CommentsDataSource>()

    override fun create(): DataSource<Int, Comment> {
        val source = CommentsDataSource(api = api, retryExecutor = networkExecutor)
        this.source.postValue(source)
        return source
    }

}