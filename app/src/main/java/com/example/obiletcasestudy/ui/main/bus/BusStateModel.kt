package com.example.obiletcasestudy.ui.main.bus

import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel

/**
 * Model for caching bus page values
 */
data class BusStateModel(
    var fromWhere: BusLocationsResponseModel.BusLocationModel?,
    var toWhere: BusLocationsResponseModel.BusLocationModel?,
    var date: String?
)