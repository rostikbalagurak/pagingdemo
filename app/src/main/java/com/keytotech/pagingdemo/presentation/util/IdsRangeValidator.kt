package com.keytotech.pagingdemo.presentation.util

import com.keytotech.pagingdemo.domain.entity.IdsRange
import javax.inject.Inject

/**
 * IdsRangeValidator
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class IdsRangeValidator @Inject constructor() {

    /**
     * Validates range of Ids, making sure that end page is bigger than start page and at least one page can be loaded
     */
    fun validate(range: IdsRange): Boolean {
        with(range) {
            return (start >= 0 && end - start >= PAGE_SIZE)
        }
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}