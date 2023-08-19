package com.example.obiletcasestudy.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obiletcasestudy.data.local.SharedPrefsManager
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.data.remote.Session
import com.example.obiletcasestudy.data.remote.SessionRequestModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import com.example.obiletcasestudy.ui.main.bus.locations.LocationTypes
import com.example.obiletcasestudy.ui.main.bus.locations.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefsManager: SharedPrefsManager,
    private val locationsRepository: LocationsRepository
) : ViewModel() {

    private val _busLocationsLiveData = MutableLiveData<Resource<BusLocationsResponseModel>>()
    val busLocationsLiveData: LiveData<Resource<BusLocationsResponseModel>> = _busLocationsLiveData

    private val _selectedBusLocationLiveData =
        MutableLiveData<Pair<LocationTypes, BusLocationsResponseModel.BusLocationModel>?>()
    val selectedBusLocationLiveData: LiveData<Pair<LocationTypes, BusLocationsResponseModel.BusLocationModel>?> =
        _selectedBusLocationLiveData

    init {
        init()
    }

    /**
     * It creates equipmentId for each time user opens application and gets session and location values
     */
    fun init() {
        var equipmentId = sharedPrefsManager.getEquipmentId()
        if (equipmentId == null) {
            equipmentId = UUID.randomUUID().toString()
            sharedPrefsManager.saveEquipmentId(equipmentId)
        }

        val sessionRequestModel =
            SessionRequestModel(application = SessionRequestModel.ApplicationModel(equipmentId = equipmentId))

        viewModelScope.launch {
            mainRepository.getSession(sessionRequestModel).collect {
                if (it is Resource.Success) {
                    Session.setSessionModel(it.data.data!!)
                    getBusLocations()
                } else if (it is Resource.Error) {
                    _busLocationsLiveData.value = it
                }
            }
        }
    }

    private fun getBusLocations() {
        viewModelScope.launch {
            locationsRepository.getBusLocations(null).collect {
                _busLocationsLiveData.value = it
            }
        }
    }

    fun setSelectedBusLocation(pair: Pair<LocationTypes, BusLocationsResponseModel.BusLocationModel>) {
        _selectedBusLocationLiveData.value = pair
        _selectedBusLocationLiveData.value = null
    }

}