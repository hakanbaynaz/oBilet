package com.example.obiletcasestudy.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Intercept requests and add #ApiClientToken for each
 */
@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader("Authorization", "Basic ${ApiUtil.ApiClientToken}")
        return chain.proceed(requestBuilder.build())
    }
}