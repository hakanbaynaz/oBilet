package com.example.obiletcasestudy.ui.main.bus.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obiletcasestudy.data.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(private val locationsRepository: LocationsRepository) : ViewModel() {

    private val _busLocationsLiveData = MutableLiveData<Resource<BusLocationsResponseModel>>()
    val busLocationsLiveData: LiveData<Resource<BusLocationsResponseModel>> = _busLocationsLiveData

    fun searchBusLocation(query: String) {
        viewModelScope.launch {
            locationsRepository.getBusLocations(query).collect {
                _busLocationsLiveData.value = it
            }
        }
    }
}