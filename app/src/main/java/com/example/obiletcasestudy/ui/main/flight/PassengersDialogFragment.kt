package com.example.obiletcasestudy.ui.main.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.obiletcasestudy.databinding.DialogFragmentPassengersBinding
import com.example.obiletcasestudy.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengersDialogFragment : BaseBottomSheetDialogFragment<DialogFragmentPassengersBinding>() {

    override fun getBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): DialogFragmentPassengersBinding {
        return DialogFragmentPassengersBinding.inflate(inflater, container, false)
    }

    override fun DialogFragmentPassengersBinding.onBindView(savedInstanceState: Bundle?) {

        val flightViewModel by activityViewModels<FlightViewModel>()

        val args by navArgs<PassengersDialogFragmentArgs>()

        counterAdult.setValue(args.passengers.adult)
        counterStudent.setValue(args.passengers.student)
        counterChild.setValue(args.passengers.child)
        counterBaby.setValue(args.passengers.baby)
        counterAbove65.setValue(args.passengers.above65)

        button.setOnClickListener {
            val passengersModel = PassengersModel(
                counterAdult.getValue(),
                counterStudent.getValue(),
                counterChild.getValue(),
                counterBaby.getValue(),
                counterAbove65.getValue()
            )
            flightViewModel.setPassengersState(passengersModel)
            this@PassengersDialogFragment.dismiss()
        }
    }
}