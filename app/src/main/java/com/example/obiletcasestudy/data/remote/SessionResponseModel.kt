package com.example.obiletcasestudy.data.remote

import com.fasterxml.jackson.annotation.JsonProperty

data class SessionResponseModel(val data: SessionModel?) : BaseResponseModel() {

    data class SessionModel(
        @JsonProperty("session-id") val sessionId: String,
        @JsonProperty("device-id") val deviceId: String
    )
}