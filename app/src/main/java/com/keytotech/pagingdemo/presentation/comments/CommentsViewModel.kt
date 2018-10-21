package com.keytotech.pagingdemo.presentation.comments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.domain.entity.Comment
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
    private val repoResult: LiveData<CommentListing<Comment>> = map(commentsData) {
        listingProvider.comments(pageSize)
    }

    val commentsList: LiveData<PagedList<Comment>> = switchMap(repoResult) { it.pagedList }
    val networkResource: LiveData<NetworkResource<*>> = switchMap(repoResult) { it.networkResource }
    val refreshResource: LiveData<NetworkResource<*>> = switchMap(repoResult) { it.refreshResource }

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