package com.example.obiletcasestudy.ui.main.bus.journey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.databinding.JourneyItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.Locale

class BusJourneyAdapter(private val dataList: List<BusJourneyResponseModel.JourneyDataModel>) :
    Adapter<BusJourneyAdapter.JourneyViewHolder>() {

    private val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val mHourDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val binding =
            JourneyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JourneyViewHolder(binding, mSimpleDateFormat, mHourDateFormat)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    class JourneyViewHolder(
        private val journeyItemLayoutBinding: JourneyItemLayoutBinding,
        private val simpleDateFormat: SimpleDateFormat,
        private val hourDateFormat: SimpleDateFormat
    ) : ViewHolder(journeyItemLayoutBinding.root) {

        fun bind(journeyDataModel: BusJourneyResponseModel.JourneyDataModel) {
            val departureDate = simpleDateFormat.parse(journeyDataModel.journey.departure)
            val arrivalDate = simpleDateFormat.parse(journeyDataModel.journey.arrival)

            with(journeyItemLayoutBinding) {
                if (departureDate != null && arrivalDate != null) {
                    textViewDate.text = itemView.context.getString(
                        R.string.departure_arrival_time,
                        hourDateFormat.format(departureDate),
                        hourDateFormat.format(arrivalDate)
                    )
                }

                textViewLocation.text = itemView.context.getString(
                    R.string.journey_location,
                    journeyDataModel.journey.origin,
                    journeyDataModel.journey.destination
                )
                textViewPrice.text = itemView.context.getString(
                    R.string.tl_args,
                    "%.2f".format(journeyDataModel.journey.price).replace(".", ",")
                )
            }

        }
    }
}