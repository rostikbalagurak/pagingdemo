package com.keytotech.pagingdemo.data.models

import com.google.gson.annotations.SerializedName

data class Comment(@SerializedName("name")
                   val name: String = "",
                   @SerializedName("postId")
                   val postId: Int = 0,
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("body")
                   val body: String = "",
                   @SerializedName("email")
                   val email: String = "")