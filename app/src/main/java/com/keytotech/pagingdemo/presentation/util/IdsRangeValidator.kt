package com.keytotech.pagingdemo.presentation.util

import com.keytotech.pagingdemo.domain.entity.IdsRange
import javax.inject.Inject


class IdsRangeValidator @Inject constructor() {

    fun validate(range: IdsRange): Boolean {
        with(range) {
            return (start >= 0 && end - start >= PAGE_SIZE)
        }
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}