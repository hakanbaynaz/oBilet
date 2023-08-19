package com.example.obiletcasestudy.ui.main.bus.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.obiletcasestudy.databinding.LocationItemLayoutBinding

class LocationsAdapter(
    private val onItemClickedListener: ((BusLocationsResponseModel.BusLocationModel) -> Unit)
) : Adapter<LocationsAdapter.LocationViewHolder>() {

    private val mDataList = mutableListOf<BusLocationsResponseModel.BusLocationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            LocationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setDataList(dataList: List<BusLocationsResponseModel.BusLocationModel>) {
        mDataList.clear()
        mDataList.addAll(dataList)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(mDataList[position])
        holder.itemView.setOnClickListener {
            onItemClickedListener.invoke(mDataList[position])
        }
    }

    class LocationViewHolder(private val locationItemLayoutBinding: LocationItemLayoutBinding) :
        ViewHolder(locationItemLayoutBinding.root) {
        fun bind(locationModel: BusLocationsResponseModel.BusLocationModel) {
            locationItemLayoutBinding.textView.text = locationModel.name
        }
    }
}