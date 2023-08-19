package com.example.obiletcasestudy.ui.main.bus.locations

import android.os.Parcelable
import com.example.obiletcasestudy.data.remote.BaseResponseModel
import kotlinx.parcelize.Parcelize

data class BusLocationsResponseModel(val data: List<BusLocationModel>?) : BaseResponseModel() {
    @Parcelize
    data class BusLocationModel(val id: Int, val name: String) : Parcelable
}