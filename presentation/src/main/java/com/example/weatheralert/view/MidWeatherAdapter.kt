package com.example.weatheralert.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ShortWeatherEntity
import com.example.weatheralert.databinding.ItemMidWeatherBinding

class MidWeatherAdapter : ListAdapter<ShortWeatherEntity, MidWeatherAdapter.MidWeatherViewHolder>(ShortWeatherAdapter.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MidWeatherViewHolder {
        return MidWeatherViewHolder(
            ItemMidWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MidWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MidWeatherViewHolder(
        private val binding: ItemMidWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShortWeatherEntity) {
            binding.day.text = item.date
            binding.tmp.text = "임시온도"
        }
    }
}