package com.example.obiletcasestudy.ui.main

import com.example.obiletcasestudy.data.remote.ApiInterface
import com.example.obiletcasestudy.data.remote.NetworkResource
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.data.remote.SessionRequestModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor(private val apiInterface: ApiInterface) :
    NetworkResource() {

    suspend fun getSession(sessionRequestModel: SessionRequestModel) = flow {
        emit(Resource.Loading)
        emit(apiCall { apiInterface.getSession(sessionRequestModel) })
    }.flowOn(Dispatchers.IO)
}