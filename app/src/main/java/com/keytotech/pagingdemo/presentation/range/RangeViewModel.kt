package com.keytotech.pagingdemo.presentation.range

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.domain.entity.IdsRange
import com.keytotech.pagingdemo.presentation.util.IdsRangeValidator
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * RangeViewModel
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class RangeViewModel @Inject constructor(private val idsRangeValidator: IdsRangeValidator) : ViewModel() {

    private val range = MutableLiveData<NetworkResource<IdsRange>>()

    private var disposable: Disposable? = null

    fun validate(range: IdsRange) {
        if (idsRangeValidator.validate(range)) {
            this.startLoading(range)
        } else {
            this.range.postValue(NetworkResource.failed(null))
        }
    }

    fun cancelLoading() {
        this.disposable?.dispose()
        this.disposable = null
    }

    private fun startLoading(range: IdsRange) {
        this.range.postValue(NetworkResource.running())
        this.disposable = Observable.interval(DELAY_SECONDS, TimeUnit.SECONDS)
            .take(1)
            .subscribe {
                this.range.postValue(NetworkResource.success(range))
            }
    }

    override fun onCleared() {
        cancelLoading()
    }

    fun range(): LiveData<NetworkResource<IdsRange>> {
        return range
    }

    companion object {
        private const val DELAY_SECONDS = 3L
    }
}