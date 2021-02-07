package com.employeecare.data.model

import com.google.gson.annotations.SerializedName

data class UserPosts(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String = ""
)