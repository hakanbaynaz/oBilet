package com.example.obiletcasestudy.ui.main.bus

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.databinding.FragmentBusTicketLayoutBinding
import com.example.obiletcasestudy.ui.base.BaseFragment
import com.example.obiletcasestudy.ui.common.AppDialog
import com.example.obiletcasestudy.ui.main.MainViewModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import com.example.obiletcasestudy.ui.main.bus.locations.LocationTypes
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class BusFragment : BaseFragment<FragmentBusTicketLayoutBinding>() {

    private val mBusViewModel by activityViewModels<BusViewModel>()

    override fun getBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentBusTicketLayoutBinding {
        return FragmentBusTicketLayoutBinding.inflate(inflater, container, false)
    }

    override fun FragmentBusTicketLayoutBinding.onBindView(savedInstanceState: Bundle?) {

        val mainViewModel by activityViewModels<MainViewModel>()

        mainViewModel.busLocationsLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                val lastState = mBusViewModel.busStateLiveData.value
                //Since there is a state we do not need to bind this data
                if (lastState?.toWhere != null && lastState.fromWhere != null) {
                    return@observe
                }
                val fromModel = it.data.data?.get(0)
                val toModel = it.data.data?.get(1)
                setLocations(fromModel, toModel)
            }
        }

        // It triggers when user changes the any location
        mainViewModel.selectedBusLocationLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.first == LocationTypes.FROM_WHERE) {
                    setLocations(
                        it.second, mBusViewModel.busStateLiveData.value?.toWhere
                    )
                } else {
                    setLocations(
                        mBusViewModel.busStateLiveData.value?.fromWhere, it.second,
                    )
                }
            }
        }

        // Since mBusViewModel is activity scoped we can reach the last state of values even fragment has destroyed
        mBusViewModel.busStateLiveData.observe(viewLifecycleOwner) {
            viewBinding.textViewFrom.text = it.fromWhere?.name
            viewBinding.textViewTo.text = it.toWhere?.name
            textViewDate.text = it.date
        }

        imageButtonSwap.setOnClickListener {
            setLocations(
                mBusViewModel.busStateLiveData.value?.toWhere,
                mBusViewModel.busStateLiveData.value?.fromWhere,
            )
        }

        cardViewFrom.setOnClickListener {
            findNavController().navigate(
                BusFragmentDirections.actionBusFragmentToLocationsDialogFragment(LocationTypes.FROM_WHERE)
            )
        }
        cardViewTo.setOnClickListener {
            findNavController().navigate(
                BusFragmentDirections.actionBusFragmentToLocationsDialogFragment(LocationTypes.TO_WHERE)
            )
        }

        buttonToday.setOnClickListener {
            mBusViewModel.setDateState(Date())
        }
        buttonTomorrow.setOnClickListener {
            val calendarTomorrow = Calendar.getInstance()
            calendarTomorrow.add(Calendar.DATE, 1)
            mBusViewModel.setDateState(calendarTomorrow.time)
        }

        cardViewDate.setOnClickListener {
            val date = mBusViewModel.getLastDate()
            val calendar = Calendar.getInstance()
            calendar.time = date
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    mBusViewModel.setDateState(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = Date().time
            datePickerDialog.show()
        }

        buttonFind.setOnClickListener {
            val fromModel = mBusViewModel.busStateLiveData.value?.fromWhere
            val toModel = mBusViewModel.busStateLiveData.value?.toWhere
            if (fromModel != null && toModel != null) {
                val directions = BusFragmentDirections.actionBusFragmentToBusJourneyActivity(
                    "${fromModel.name} - ${toModel.name}",
                    fromModel.id,
                    toModel.id,
                    mBusViewModel.getLastDate()
                )
                findNavController().navigate(directions)
            }
        }
    }

    /**
     * It validates the locations and passes to the viewModel
     */
    private fun setLocations(
        from: BusLocationsResponseModel.BusLocationModel?,
        to: BusLocationsResponseModel.BusLocationModel?
    ) {
        if (from == to) {
            AppDialog.showSimpleErrorDialog(
                requireContext(), getString(R.string.same_locations_error_message)
            )
            return
        }
        if (from != null && to != null) {
            mBusViewModel.setLocationsState(from, to)
        }
    }
}