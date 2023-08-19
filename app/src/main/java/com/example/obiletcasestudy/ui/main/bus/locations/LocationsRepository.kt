package com.example.obiletcasestudy.ui.main.bus.locations

import com.example.obiletcasestudy.data.remote.ApiInterface
import com.example.obiletcasestudy.data.remote.NetworkResource
import com.example.obiletcasestudy.data.remote.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class LocationsRepository @Inject constructor(private val apiInterface: ApiInterface) :
    NetworkResource() {

    suspend fun getBusLocations(query: String?) = flow {
        emit(Resource.Loading)
        emit(apiCall { apiInterface.getBusLocations(BusLocationsRequestModel(data = query)) })
    }.flowOn(Dispatchers.IO)
}