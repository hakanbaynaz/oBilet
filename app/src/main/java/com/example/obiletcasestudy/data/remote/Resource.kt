package com.example.obiletcasestudy.data.remote

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val baseResponseModel: BaseResponseModel) : Resource<Nothing>()
}