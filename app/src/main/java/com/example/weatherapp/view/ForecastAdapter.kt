package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.ForecastItems
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.domain.Util

class ForecastAdapter :
    ListAdapter<ForecastItems, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastItems) {
            val temp = item.main.temp
            temp.let {
                val celsiusTemperature =
                    Util.formatTemperature(Util.kelvinToCelsius(it))

                val temperatureText = itemView.context.getString(
                    R.string.temperature_format,
                    celsiusTemperature,
                    itemView.context.getString(R.string.unit_celsius)
                )

                binding.dateTextView.text = Util.getWeekdayNameFromDate(item.dt_txt)
                binding.tempTextView.text = temperatureText
            }
        }
    }

    private class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastItems>() {
        override fun areItemsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
            return oldItem.dt_txt == newItem.dt_txt
        }

        override fun areContentsTheSame(oldItem: ForecastItems, newItem: ForecastItems): Boolean {
            return oldItem == newItem
        }
    }
}