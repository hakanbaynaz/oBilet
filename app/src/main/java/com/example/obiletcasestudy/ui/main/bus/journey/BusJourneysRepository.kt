package com.example.obiletcasestudy.ui.main.bus.journey

import com.example.obiletcasestudy.data.remote.ApiInterface
import com.example.obiletcasestudy.data.remote.NetworkResource
import com.example.obiletcasestudy.data.remote.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class BusJourneysRepository @Inject constructor(private val apiInterface: ApiInterface) :
    NetworkResource() {

    suspend fun getJourneys(journeyRequestModel: BusJourneyRequestModel) = flow {
        emit(Resource.Loading)
        emit(apiCall { apiInterface.getJourneys(journeyRequestModel) })
    }.flowOn(Dispatchers.IO)
}