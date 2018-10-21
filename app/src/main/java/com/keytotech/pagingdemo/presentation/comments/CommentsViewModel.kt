package com.keytotech.pagingdemo.presentation.comments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.keytotech.pagingdemo.data.repository.CommentsRepository
import javax.inject.Inject

/**
 * CommentsViewModel
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class CommentsViewModel @Inject constructor(private val repository: CommentsRepository) : ViewModel() {

    private val pageSize = 30
    var commentsData = MutableLiveData<Int>()
    private val repoResult = map(commentsData) {
        repository.comments(pageSize)
    }
    val commentsList = switchMap(repoResult) { it.pagedList }!!
    val networkState = switchMap(repoResult) { it.networkState }!!
    val refreshState = switchMap(repoResult) { it.refreshState }!!


    fun fetch() {
        this.commentsData.value = pageSize
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

}