package com.example.obiletcasestudy.ui.main.bus.journey

import com.example.obiletcasestudy.data.remote.BaseRequestModel
import com.fasterxml.jackson.annotation.JsonProperty

data class BusJourneyRequestModel(val data: BusJourneyDataModel) : BaseRequestModel() {

    data class BusJourneyDataModel(
        @JsonProperty("origin-id") val originId: Int,
        @JsonProperty("destination-id") val destinationId: Int,
        @JsonProperty("departure-date") val departureDate: String
    )
}