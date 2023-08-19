package com.example.obiletcasestudy.ui.main.bus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(private val simpleDateFormat: SimpleDateFormat) :
    ViewModel() {

    private val _busStateLiveData = MutableLiveData<BusStateModel>()
    val busStateLiveData: LiveData<BusStateModel> = _busStateLiveData

    /**
     * Initialize default values for bus page, since we do not have location values here it is safe to use null for the first time.
     */
    init {
        val calendarTomorrow = Calendar.getInstance()
        calendarTomorrow.add(Calendar.DATE, 1)
        _busStateLiveData.value =
            BusStateModel(null, null, simpleDateFormat.format(calendarTomorrow.time))
    }

    /**
     * Store location values and emit to ui
     */
    fun setLocationsState(
        fromWhere: BusLocationsResponseModel.BusLocationModel,
        toWhere: BusLocationsResponseModel.BusLocationModel
    ) {
        val busStateModel = _busStateLiveData.value!!
        busStateModel.fromWhere = fromWhere
        busStateModel.toWhere = toWhere
        _busStateLiveData.value = busStateModel
    }

    /**
     * Store date value and emit to ui
     */
    fun setDateState(date: Date) {
        val busStateModel = _busStateLiveData.value!!
        busStateModel.date = simpleDateFormat.format(date)
        _busStateLiveData.value = busStateModel
    }

    fun getLastDate() = simpleDateFormat.parse(_busStateLiveData.value!!.date!!)!!
}