package com.example.obiletcasestudy.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.Locale

@InstallIn(SingletonComponent::class)
@Module
object Util {

    @Provides
    fun getDateFormatter(): SimpleDateFormat =
        SimpleDateFormat("d MMMM yyyy EEEE", Locale.getDefault())
}