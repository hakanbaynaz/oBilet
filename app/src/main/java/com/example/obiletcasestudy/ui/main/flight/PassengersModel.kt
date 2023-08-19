package com.example.obiletcasestudy.ui.main.flight

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PassengersModel(
    val adult: Int, val student: Int, val child: Int, val baby: Int, val above65: Int
) : Parcelable