package com.example.obiletcasestudy.ui.main.flight

import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel

/**
 * Model for caching flight page values
 */
class FlightStateModel(
    var fromWhere: BusLocationsResponseModel.BusLocationModel?,
    var toWhere: BusLocationsResponseModel.BusLocationModel?,
    var outGoingDate: String?,
    var returningDate: String?,
    var passengersModel: PassengersModel
)