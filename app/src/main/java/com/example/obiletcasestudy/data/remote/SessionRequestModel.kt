package com.example.obiletcasestudy.data.remote

import com.example.obiletcasestudy.BuildConfig
import com.fasterxml.jackson.annotation.JsonProperty

data class SessionRequestModel(
    val type: Int = 3,
    val application: ApplicationModel,
    val connection: ConnectionModel = ConnectionModel()
) {

    data class ConnectionModel(@JsonProperty("ip-address") val ipAddress: String = "")

    data class ApplicationModel(
        val version: String = "${BuildConfig.VERSION_CODE}.${BuildConfig.VERSION_NAME}",
        @JsonProperty("equipment-id") val equipmentId: String
    )
}