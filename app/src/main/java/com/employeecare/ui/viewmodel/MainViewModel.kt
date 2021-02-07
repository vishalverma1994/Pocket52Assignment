package com.employeecare.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.employeecare.data.model.UserDataModel
import com.employeecare.data.model.UserPosts
import com.employeecare.data.repository.MainRepository
import com.employeecare.utils.NO_INTERNET_CONNECTION_MESSAGE
import com.employeecare.utils.NetworkHelper
import com.employeecare.utils.Resource
import kotlinx.coroutines.launch


class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {


    val _userPostListResponse = MutableLiveData<Resource<List<UserPosts>>>()
    val _userDetailResponse = MutableLiveData<Resource<UserDataModel>>()

    fun requestUserPostListAPI() {
        viewModelScope.launch {
            _userPostListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.requestUserPostListAPI()
                    .let { response ->
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let { postList ->
                                _userPostListResponse.postValue(Resource.success(postList))
                            }
                        } else _userPostListResponse.postValue(
                            Resource.error(
                                response.body().toString(),
                                null
                            )
                        )
                    }
            } else _userPostListResponse.postValue(
                Resource.error(
                    NO_INTERNET_CONNECTION_MESSAGE,
                    null
                )
            )
        }
    }

    fun requestUserDetailsAPI(userId: Int) {
        viewModelScope.launch {
            _userDetailResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.requestUserDetailsAPI(userId)
                    .let { response ->
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let { userDetail ->
                                _userDetailResponse.postValue(Resource.success(userDetail))
                            }
                        } else _userDetailResponse.postValue(
                            Resource.error(
                                response.body().toString(), null
                            )
                        )
                    }
            } else _userDetailResponse.postValue(
                Resource.error(
                    NO_INTERNET_CONNECTION_MESSAGE,
                    null
                )
            )
        }
    }

}