package com.keytotech.pagingdemo.presentation.range

/**
 * RangePresenter
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class RangePresenter(private val view: RangeView) {

    fun validate(start: Int, end: Int) {
        if (start > 0 && end > 0 && start < end) {
            this.view.openCommentsScreen(start, end)
        } else {
            this.view.showInvalidRangeError()
        }
    }
}