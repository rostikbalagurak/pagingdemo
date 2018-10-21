package com.keytotech.pagingdemo.domain.entity

data class CommentEntity(
    val name: String = "",
    val postId: Int = 0,
    val id: Int = 0,
    val body: String = "",
    val email: String = ""
)