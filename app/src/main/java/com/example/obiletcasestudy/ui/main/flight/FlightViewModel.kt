package com.example.obiletcasestudy.ui.main.flight

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.ui.common.AppDialog
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(private val simpleDateFormat: SimpleDateFormat) :
    ViewModel() {

    private val _flightStateLiveData = MutableLiveData<FlightStateModel>()
    val flightStateLiveData: LiveData<FlightStateModel> = _flightStateLiveData

    /**
     * Initialize default values for flight page, since we do not have location values here it is safe to use null for the first time.
     */
    init {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        _flightStateLiveData.value = FlightStateModel(
            null,
            null,
            simpleDateFormat.format(Date()),
            simpleDateFormat.format(calendar.time),
            PassengersModel(1, 0, 0, 0, 0)
        )
    }

    /**
     * Store location values and emit to ui
     */
    fun setLocationsState(
        fromWhere: BusLocationsResponseModel.BusLocationModel,
        toWhere: BusLocationsResponseModel.BusLocationModel
    ) {
        val flightStateModel = flightStateLiveData.value!!
        flightStateModel.fromWhere = fromWhere
        flightStateModel.toWhere = toWhere
        _flightStateLiveData.value = flightStateModel
    }

    /**
     * Store date values and emit to ui
     */
    fun setDateState(outGoingDate: Date, returningDate: Date?) {
        val flightStateModel = _flightStateLiveData.value!!
        flightStateModel.outGoingDate = simpleDateFormat.format(outGoingDate)
        flightStateModel.returningDate =
            if (returningDate == null) null else simpleDateFormat.format(returningDate)
        _flightStateLiveData.value = flightStateModel
    }

    /**
     * Store passengers value and emit to ui
     */
    fun setPassengersState(passengersModel: PassengersModel) {
        val flightStateModel = _flightStateLiveData.value!!
        flightStateModel.passengersModel = passengersModel
        _flightStateLiveData.value = flightStateModel
    }

    fun getOutGoingDate() = simpleDateFormat.parse(flightStateLiveData.value!!.outGoingDate!!)!!

    fun getReturningDate(): Date? {
        val dateString = flightStateLiveData.value?.returningDate
        return if (dateString == null) {
            null
        } else {
            simpleDateFormat.parse(dateString)!!
        }
    }

    /**
     * It checks the dates and shows error dialog if it not valid
     * @return true if dates are valid else otherwise
     */
    fun validateDates(context: Context, outGoing: Date, returning: Date?): Boolean {
        if (returning == null) {
            return true
        }
        val calendarOutGoing = Calendar.getInstance()
        calendarOutGoing.set(Calendar.HOUR_OF_DAY, 0)
        calendarOutGoing.set(Calendar.MINUTE, 0)
        calendarOutGoing.set(Calendar.SECOND, 0)
        calendarOutGoing.set(Calendar.MILLISECOND, 0)
        calendarOutGoing.time = outGoing

        val calendarReturning = Calendar.getInstance()
        calendarReturning.set(Calendar.HOUR_OF_DAY, 0)
        calendarReturning.set(Calendar.MINUTE, 0)
        calendarReturning.set(Calendar.SECOND, 0)
        calendarReturning.set(Calendar.MILLISECOND, 0)
        calendarReturning.time = returning

        return if (calendarOutGoing.time.after(calendarReturning.time)) {
            AppDialog.showSimpleErrorDialog(context, context.getString(R.string.date_error))
            false
        } else {
            true
        }
    }
}