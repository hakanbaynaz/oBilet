package com.example.obiletcasestudy.ui.main.bus.journey

import com.example.obiletcasestudy.data.remote.BaseResponseModel
import com.fasterxml.jackson.annotation.JsonProperty

data class BusJourneyResponseModel(val data: List<JourneyDataModel>?) : BaseResponseModel() {

    data class JourneyDataModel(val journey: JourneyModel)

    data class JourneyModel(
        val origin: String,
        val destination: String,
        val departure: String,
        val arrival: String,
        @JsonProperty("internet-price") val price: Float
    )
}