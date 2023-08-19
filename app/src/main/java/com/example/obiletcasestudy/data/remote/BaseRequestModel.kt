package com.example.obiletcasestudy.data.remote

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class BaseRequestModel {
    val date: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
    val language: String = "tr-TR"

    @JsonProperty("device-session")
    val deviceSession: SessionResponseModel.SessionModel = Session.getSessionModel()
}