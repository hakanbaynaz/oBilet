package com.example.obiletcasestudy.data.remote

import com.example.obiletcasestudy.ui.main.bus.journey.BusJourneyRequestModel
import com.example.obiletcasestudy.ui.main.bus.journey.BusJourneyResponseModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsRequestModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("client/getsession")
    suspend fun getSession(@Body sessionRequestModel: SessionRequestModel): Response<SessionResponseModel>

    @POST("location/getbuslocations")
    suspend fun getBusLocations(@Body busLocationsRequestModel: BusLocationsRequestModel): Response<BusLocationsResponseModel>

    @POST("journey/getbusjourneys")
    suspend fun getJourneys(@Body journeyRequestModel: BusJourneyRequestModel):Response<BusJourneyResponseModel>

}