package com.keytotech.pagingdemo.presentation.comments.pagination

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.keytotech.pagingdemo.di.viewModel.NetworkResource

/**
 * CommentListing
 *
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
data class CommentListing<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val loadingState: LiveData<NetworkResource<*>>,
    // represents the refresh status to show to the user. Separate from loadingState, this
    // value is importantly only when refresh is requested.
    val refreshState: LiveData<NetworkResource<*>>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
    // retries any failed requests.
    val retry: () -> Unit
)