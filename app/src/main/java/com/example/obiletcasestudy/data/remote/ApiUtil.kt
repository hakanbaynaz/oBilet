package com.example.obiletcasestudy.data.remote

object ApiUtil {
    const val BASE_URL = "https://v2-api.obilet.com/api/"

    //todo add ApiClientToken here
    const val ApiClientToken = ""

    enum class Status(val status: String) {
        SUCCESS("Success"), INVALID_DEPARTURE_DATE("InvalidDepartureDate"), INVALID_ROUTE("InvalidRoute"), INVALID_LOCATION(
            "InvalidLocation"
        ),
        TIMEOUT("Timeout")
    }
}