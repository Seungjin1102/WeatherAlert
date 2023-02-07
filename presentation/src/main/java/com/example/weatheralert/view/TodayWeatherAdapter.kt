package com.example.weatheralert.view

import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.WeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.databinding.ItemTodayWeatherBinding

class TodayWeatherAdapter: ListAdapter<WeatherEntity, TodayWeatherAdapter.TodayWeatherViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayWeatherViewHolder {
        return TodayWeatherViewHolder(
            ItemTodayWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodayWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class TodayWeatherViewHolder(
        private val binding: ItemTodayWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherEntity) {
            binding.time.text = converterTime(item.time)
            binding.sky.setImageResource(setSkyImage(item.sky, item.pty))
            binding.tmp.text = item.tmp + "°"
            binding.popText.text = item.pop + "%"
        }

        private fun converterTime(time: String): String {
            var stringTime = ""

            (time.toInt() / 100).run {
                stringTime = if (this < 12) {
                    "오전 " + this + "시"
                } else {
                    "오후 " + (this - 12) + "시"
                }
            }

            return stringTime
        }

        private fun setSkyImage(sky: String, pty: String): Int  {
            return when(sky) {
                "1" -> R.drawable.sun
                "3" -> R.drawable.sun_cloudy
                else -> {
                    when(pty) {
                        "1", "4" -> R.drawable.rain
                        "2" -> R.drawable.snow
                        "3" -> R.drawable.rain_snow
                        else -> R.drawable.cloud
                    }
                }
            }
        }
    }

    class TodayWeatherItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            if (itemPosition != 0) outRect.left = 40
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WeatherEntity>() {
            override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}