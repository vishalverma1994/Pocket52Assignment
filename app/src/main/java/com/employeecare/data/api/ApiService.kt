package com.employeecare.data.api

import com.employeecare.data.model.UserDataModel
import com.employeecare.data.model.UserPosts
import com.employeecare.utils.API_USER_DETAIL
import com.employeecare.utils.API_USER_POST_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(API_USER_POST_LIST)
    suspend fun requestUserPostList(): Response<List<UserPosts>>

    @GET(API_USER_DETAIL)
    suspend fun requestUserDetails(@Path("userId") userId: Int): Response<UserDataModel>
}