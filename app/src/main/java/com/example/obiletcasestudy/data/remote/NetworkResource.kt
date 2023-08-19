package com.example.obiletcasestudy.data.remote

import retrofit2.Response

/**
 * It checks the state of response for every request before passing it
 */
abstract class NetworkResource {

    suspend fun <T : BaseResponseModel> apiCall(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.status == ApiUtil.Status.SUCCESS.status) {
                        return Resource.Success(it)
                    } else {
                        return Resource.Error(it)
                    }
                }
            }
            return Resource.Error(BaseResponseModel())
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Resource.Error(BaseResponseModel())
        }
    }
}