package com.keytotech.pagingdemo.domain.entity

/**
 * Comment
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
data class Comment(
    val name: String = "",
    val postId: Int = 0,
    val id: Int = 0,
    val body: String = "",
    val email: String = ""
)