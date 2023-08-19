package com.example.obiletcasestudy.data.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun getApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): Retrofit =
        Retrofit.Builder().baseUrl(ApiUtil.BASE_URL).addConverterFactory(
            JacksonConverterFactory.create(objectMapper)
        ).client(okHttpClient).build()

    @Provides
    fun getOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, tokenInterceptor: TokenInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .addInterceptor(tokenInterceptor).build()

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun getObjectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
}