package com.example.obiletcasestudy.ui.main.bus.locations

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.databinding.DialogFragmentLocationsBinding
import com.example.obiletcasestudy.ui.base.BaseBottomSheetDialogFragment
import com.example.obiletcasestudy.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsDialogFragment : BaseBottomSheetDialogFragment<DialogFragmentLocationsBinding>() {

    override fun getBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): DialogFragmentLocationsBinding {
        return DialogFragmentLocationsBinding.inflate(inflater, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun DialogFragmentLocationsBinding.onBindView(savedInstanceState: Bundle?) {
        val args by navArgs<LocationsDialogFragmentArgs>()
        val mainViewModel by activityViewModels<MainViewModel>()
        val locationsViewModel by viewModels<LocationsViewModel>()

        textViewTitle.text = if (args.locationType == LocationTypes.FROM_WHERE) {
            getString(R.string.from_where)
        } else {
            getString(R.string.to_where)
        }

        val adapter = LocationsAdapter {
            mainViewModel.setSelectedBusLocation(Pair(args.locationType, it))
            dismiss()
        }

        var defaultList: List<BusLocationsResponseModel.BusLocationModel>? = null
        //Main bus locations
        mainViewModel.busLocationsLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                defaultList = it.data.data
                adapter.setDataList(it.data.data ?: listOf())
                adapter.notifyDataSetChanged()
            }
        }

        //Search results
        locationsViewModel.busLocationsLiveData.observe(viewLifecycleOwner) {
            if (it is Resource.Success && edittextSearch.text.isNotEmpty()) {
                adapter.setDataList(it.data.data ?: listOf())
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        edittextSearch.doAfterTextChanged {
            if ((it?.length ?: 0) > 0) {
                locationsViewModel.searchBusLocation(it.toString())
            } else {//Populate default values if search is empty
                adapter.setDataList(defaultList ?: listOf())
                adapter.notifyDataSetChanged()
            }
        }
    }
}