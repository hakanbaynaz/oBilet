package com.example.obiletcasestudy.ui.main.flight

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.databinding.FragmentFlightTicketLayoutBinding
import com.example.obiletcasestudy.ui.base.BaseFragment
import com.example.obiletcasestudy.ui.common.AppDialog
import com.example.obiletcasestudy.ui.main.MainViewModel
import com.example.obiletcasestudy.ui.main.bus.locations.BusLocationsResponseModel
import com.example.obiletcasestudy.ui.main.bus.locations.LocationTypes
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FlightFragment : BaseFragment<FragmentFlightTicketLayoutBinding>() {

    private val mFlightViewModel by activityViewModels<FlightViewModel>()

    override fun getBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentFlightTicketLayoutBinding {
        return FragmentFlightTicketLayoutBinding.inflate(inflater, container, false)
    }

    override fun FragmentFlightTicketLayoutBinding.onBindView(savedInstanceState: Bundle?) {
        val mainViewModel by activityViewModels<MainViewModel>()
        val calendarTomorrow = Calendar.getInstance()
        calendarTomorrow.add(Calendar.DATE, 1)

        mainViewModel.busLocationsLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                val lastState = mFlightViewModel.flightStateLiveData.value
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
                        it.second, mFlightViewModel.flightStateLiveData.value?.toWhere
                    )
                } else {
                    setLocations(
                        mFlightViewModel.flightStateLiveData.value?.fromWhere, it.second,
                    )
                }
            }
        }

        // Since mFlightViewModel is activity scoped we can reach the last state of values even fragment has destroyed
        mFlightViewModel.flightStateLiveData.observe(viewLifecycleOwner) {
            viewBinding.textViewFrom.text = it.fromWhere?.name
            viewBinding.textViewTo.text = it.toWhere?.name
            setDates(mFlightViewModel.getOutGoingDate(), mFlightViewModel.getReturningDate())
            viewBinding.textViewPassengers.text = getString(
                R.string.passenger_count,
                it.passengersModel.adult + it.passengersModel.student + it.passengersModel.child + it.passengersModel.baby + it.passengersModel.above65
            )
        }

        imageButtonSwap.setOnClickListener {
            setLocations(
                mFlightViewModel.flightStateLiveData.value?.toWhere,
                mFlightViewModel.flightStateLiveData.value?.fromWhere
            )
        }

        cardViewFrom.setOnClickListener {
            findNavController().navigate(
                FlightFragmentDirections.actionFlightFragmentToLocationsDialogFragment(LocationTypes.FROM_WHERE)
            )
        }
        cardViewTo.setOnClickListener {
            findNavController().navigate(
                FlightFragmentDirections.actionFlightFragmentToLocationsDialogFragment(LocationTypes.TO_WHERE)
            )
        }

        imageButtonClear.setOnClickListener {
            mFlightViewModel.setDateState(
                mFlightViewModel.getOutGoingDate(), null
            )
        }

        viewOutGoing.setOnClickListener {
            openCalendarDialog(false)
        }

        imageButtonAddReturning.setOnClickListener {
            openCalendarDialog(true)
        }

        viewReturning.setOnClickListener {
            if (imageButtonAddReturning.visibility == View.INVISIBLE) {
                openCalendarDialog(true)
            }
        }

        cardViewPerson.setOnClickListener {
            val directions =
                FlightFragmentDirections.actionFlightFragmentToPassengersDialogFragment(
                    mFlightViewModel.flightStateLiveData.value!!.passengersModel
                )
            findNavController().navigate(directions)
        }

        buttonFind.setOnClickListener {
            val fromModel = mFlightViewModel.flightStateLiveData.value?.fromWhere
            val toModel = mFlightViewModel.flightStateLiveData.value?.toWhere
            if (fromModel != null && toModel != null) {
                val directions = FlightFragmentDirections.actionFlightFragmentToBusJourneyActivity(
                    "${fromModel.name} - ${toModel.name}",
                    fromModel.id,
                    toModel.id,
                    mFlightViewModel.getOutGoingDate()
                )
                findNavController().navigate(directions)
            }
        }
    }

    /**
     * It handles outGoingDate or ReturningDate changes and passes to viewModel
     */
    private fun openCalendarDialog(isReturning: Boolean) {
        val outGoingDate = mFlightViewModel.getOutGoingDate()
        val returningDate = mFlightViewModel.getReturningDate()

        val calendar = Calendar.getInstance()
        if (isReturning) {
            if (returningDate == null) {
                calendar.add(Calendar.DATE, 1)
            } else {
                calendar.time = returningDate
            }
        } else {
            calendar.time = outGoingDate
        }
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val selectedOutGoingDate = if (isReturning) outGoingDate else calendar.time
                val selectedReturningDate = if (isReturning) calendar.time else returningDate
                if (!mFlightViewModel.validateDates(
                        requireContext(), selectedOutGoingDate, selectedReturningDate
                    )
                ) {
                    return@DatePickerDialog
                }
                mFlightViewModel.setDateState(selectedOutGoingDate, selectedReturningDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = Date().time
        datePickerDialog.show()
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
            mFlightViewModel.setLocationsState(from, to)
        }
    }

    /**
     * Bind date values to ui
     */
    private fun FragmentFlightTicketLayoutBinding.setDates(outGoing: Date, returning: Date?) {
        val dayNumberFormatter = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
        val dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())

        textViewOutgoingDayNumber.text = dayNumberFormatter.format(outGoing)
        textViewOutgoingMonthDay.text = getString(
            R.string.month_day, monthFormatter.format(outGoing), dayFormatter.format(outGoing)
        )

        if (returning == null) {
            textViewReturningDayNumber.visibility = View.INVISIBLE
            textViewReturningMonthDay.visibility = View.INVISIBLE
            imageButtonClear.visibility = View.INVISIBLE
            imageButtonAddReturning.visibility = View.VISIBLE
        } else {
            textViewReturningDayNumber.visibility = View.VISIBLE
            textViewReturningMonthDay.visibility = View.VISIBLE
            imageButtonClear.visibility = View.VISIBLE
            imageButtonAddReturning.visibility = View.INVISIBLE
            textViewReturningDayNumber.text = dayNumberFormatter.format(returning)
            textViewReturningMonthDay.text = getString(
                R.string.month_day, monthFormatter.format(returning), dayFormatter.format(returning)
            )
        }
    }
}