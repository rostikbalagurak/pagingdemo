package com.keytotech.pagingdemo.presentation.comments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.keytotech.pagingdemo.di.viewModel.NetworkState
import com.keytotech.pagingdemo.domain.entity.CommentEntity
import com.keytotech.pagingdemo.presentation.comments.pagination.CommentListing
import com.keytotech.pagingdemo.presentation.comments.pagination.CommentsListingProvider
import javax.inject.Inject

/**
 * CommentsViewModel
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsViewModel @Inject constructor(private val listingProvider: CommentsListingProvider) : ViewModel() {

    private val pageSize = 30
    var commentsData = MutableLiveData<Int>()
    private val repoResult: LiveData<CommentListing<CommentEntity>> = map(commentsData) {
        listingProvider.comments(pageSize)
    }

    val commentsList: LiveData<PagedList<CommentEntity>> = switchMap(repoResult) { it.pagedList }
    val networkState: LiveData<NetworkState> = switchMap(repoResult) { it.networkState }
    val refreshState: LiveData<NetworkState> = switchMap(repoResult) { it.refreshState }

    fun fetch() {
        this.commentsData.value = pageSize
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

}