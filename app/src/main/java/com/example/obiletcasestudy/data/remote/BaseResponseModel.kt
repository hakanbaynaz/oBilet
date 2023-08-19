package com.example.obiletcasestudy.data.remote

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseResponseModel{
    val status: String? = null
    val message: String? = null
    @JsonProperty("user-message")
    val userMessage: String? = null
}