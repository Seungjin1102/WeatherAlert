package com.example.weatheralert.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MidWeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.databinding.ItemMidWeatherBinding

class MidWeatherAdapter : ListAdapter<MidWeatherEntity, MidWeatherAdapter.MidWeatherViewHolder>(diffUtil) {

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

        fun bind(item: MidWeatherEntity) {
            binding.day.text = "테스트 날짜"
            binding.tmp.text = "${item.midTmp.minTmp}° / ${item.midTmp.maxTmp}"
            binding.skyPm.setImageResource(setSkyImage(item.midSky.pmSky))
            binding.popPm.text = "${item.midSky.pmPop}%"
            binding.skyAm.setImageResource(setSkyImage(item.midSky.amSky))
            binding.popAm.text = "${item.midSky.amPop}%"
        }

        private fun setSkyImage(sky: String): Int  {
            return when(sky) {
                "구름많음" -> R.drawable.sun_cloudy//R.drawable.sun
                "흐림" -> R.drawable.cloud
                "맑음" -> R.drawable.sun
                else -> R.drawable.sun
            }
        }
    }



    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MidWeatherEntity>() {
            override fun areItemsTheSame(oldItem: MidWeatherEntity, newItem: MidWeatherEntity): Boolean {
                return oldItem.midTmp == newItem.midTmp
            }

            override fun areContentsTheSame(oldItem: MidWeatherEntity, newItem: MidWeatherEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}