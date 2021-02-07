package com.employeecare.data.api

import com.employeecare.data.model.UserDataModel
import com.employeecare.data.model.UserPosts
import retrofit2.Response

interface ApiHelper {

    suspend fun requestUserPostList(): Response<List<UserPosts>>

    suspend fun requestUserDetails(userId: Int): Response<UserDataModel>
}