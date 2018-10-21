package com.keytotech.pagingdemo.presentation.comments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.di.viewModel.Status
import com.keytotech.pagingdemo.domain.entity.Comment
import com.keytotech.pagingdemo.domain.entity.IdsRange
import com.keytotech.pagingdemo.presentation.comments.pagination.CommentListing
import com.keytotech.pagingdemo.presentation.comments.pagination.CommentsListingProvider
import javax.inject.Inject

/**
 * CommentsViewModel
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsViewModel @Inject constructor(private val listingProvider: CommentsListingProvider) : ViewModel() {

    private val pageSize = 10
    private var pagination = MutableLiveData<Pagination>()
    private val repoResult: LiveData<CommentListing<Comment>> = map(pagination) {
        listingProvider.comments(it)
    }

    val commentsList: LiveData<PagedList<Comment>> = switchMap(repoResult) { it.pagedList }
    val loadingState: LiveData<Status> = switchMap(repoResult) { it.loadingState }
    val refreshState: LiveData<Status> = switchMap(repoResult) { it.refreshState }

    fun fetch(range: IdsRange) {
        val initialPage: Int = (range.start / pageSize) + 1
        val lastPage: Int = (range.end / pageSize)
        this.pagination.value = Pagination(pageSize, initialPage, lastPage)
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }
}

data class Pagination(val limit: Int, val startPage: Int, val endPage: Int)