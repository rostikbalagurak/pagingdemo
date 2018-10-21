package com.keytotech.pagingdemo.presentation.range

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.keytotech.pagingdemo.di.viewModel.NetworkResource
import com.keytotech.pagingdemo.domain.entity.IdsRange
import com.keytotech.pagingdemo.presentation.util.IdsRangeValidator
import javax.inject.Inject

/**
 * RangeViewModel
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class RangeViewModel @Inject constructor(private val idsRangeValidator: IdsRangeValidator) : ViewModel() {

    private val range = MutableLiveData<NetworkResource<IdsRange>>()

    fun validate(range: IdsRange) {
        if (idsRangeValidator.validate(range)) {
            this.range.postValue(NetworkResource.success(range))
        } else {
            this.range.postValue(NetworkResource.failed(null))
        }
    }

    fun range(): LiveData<NetworkResource<IdsRange>> {
        return range
    }
}