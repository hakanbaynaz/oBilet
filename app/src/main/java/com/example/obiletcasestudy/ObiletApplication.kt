package com.example.obiletcasestudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ObiletApplication @Inject constructor() : Application()