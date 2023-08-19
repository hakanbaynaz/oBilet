package com.example.obiletcasestudy.ui.main.bus.journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obiletcasestudy.data.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusJourneyViewModel @Inject constructor(private val busJourneysRepository: BusJourneysRepository) :
    ViewModel() {

    private val _busJourneysLiveData = MutableLiveData<Resource<BusJourneyResponseModel>>()
    val busJourneysLiveData: LiveData<Resource<BusJourneyResponseModel>> = _busJourneysLiveData

    fun getJourneys(originId: Int, destinationId: Int, departureDate: String) {
        viewModelScope.launch {
            busJourneysRepository.getJourneys(
                BusJourneyRequestModel(
                    BusJourneyRequestModel.BusJourneyDataModel(
                        originId, destinationId, departureDate
                    )
                )
            ).collect {
                _busJourneysLiveData.value = it
            }
        }
    }
}