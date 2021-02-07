package com.employeecare.data.repository

import com.employeecare.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun requestUserPostListAPI() = apiHelper.requestUserPostList()

    suspend fun requestUserDetailsAPI(userId: Int) = apiHelper.requestUserDetails(userId)

}