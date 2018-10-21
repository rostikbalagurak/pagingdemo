package com.keytotech.pagingdemo.presentation.range

/**
 * RangeView
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface RangeView {
    fun openCommentsScreen(start: Int, end: Int)
    fun showInvalidRangeError()
}