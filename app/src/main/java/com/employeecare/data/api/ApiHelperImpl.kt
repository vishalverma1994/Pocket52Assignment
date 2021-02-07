package com.employeecare.data.api

import com.employeecare.data.model.UserDataModel
import com.employeecare.data.model.UserPosts
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun requestUserPostList(): Response<List<UserPosts>> = apiService.requestUserPostList()

    override suspend fun requestUserDetails(userId: Int): Response<UserDataModel> = apiService.requestUserDetails(userId)

}