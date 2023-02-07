package com.example.weatheralert.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WeatherEntity
import com.example.weatheralert.databinding.ItemWeeklyWeatherBinding

class WeeklyWeatherAdapter : ListAdapter<WeatherEntity, WeeklyWeatherAdapter.WeeklyWeatherViewHolder>(TodayWeatherAdapter.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherViewHolder {
        return WeeklyWeatherViewHolder(
            ItemWeeklyWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WeeklyWeatherViewHolder(
        private val binding: ItemWeeklyWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherEntity) {
            binding.day.text = item.date
            binding.tmp.text = "임시온도"
        }
    }
}