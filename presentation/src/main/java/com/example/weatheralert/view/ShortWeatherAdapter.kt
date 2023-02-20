package com.example.weatheralert.view

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.ShortWeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.databinding.ItemShortWeatherBinding
import com.example.weatheralert.util.ResourceUtil

class ShortWeatherAdapter : ListAdapter<ShortWeatherEntity, ShortWeatherAdapter.ShortWeatherViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortWeatherViewHolder {
        return ShortWeatherViewHolder(
            ItemShortWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShortWeatherViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }


    class ShortWeatherViewHolder(
        private val binding: ItemShortWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShortWeatherEntity, position: Int) {
            binding.time.text = if (item.time == "0000") {
                if (position in 1..23) ResourceUtil.getString(R.string.common_tomorrow)
                else ResourceUtil.getString(R.string.common_after_tomorrow)
            } else {
                converterTime(item.time)
            }
            binding.sky.setImageResource(setSkyImage(item.sky, item.pty, item.time))
            binding.tmp.text = item.tmp + ResourceUtil.getString(R.string.common_temperature_sign)
            binding.popText.text = item.pop + ResourceUtil.getString(R.string.common_percent_sign)
        }

        private fun converterTime(time: String): String {
            var stringTime = ""

            (time.toInt() / 100).run {
                stringTime = if (this < 12) {
                    ResourceUtil.getString(R.string.common_morning) + " " + this + "시"
                } else {
                    if (this == 12) ResourceUtil.getString(R.string.common_afternoon) + " 12" + ResourceUtil.getString(R.string.common_oclock)
                    else ResourceUtil.getString(R.string.common_afternoon) + " " + (this - 12) + ResourceUtil.getString(R.string.common_oclock)
                }
            }

            return stringTime
        }

        private fun setSkyImage(sky: String, pty: String, time: String): Int  {
            return when(sky) {
                "1" -> if ((time.toInt() / 100) in 7..18) R.drawable.sun else R.drawable.moon
                "3" -> if ((time.toInt() / 100) in 7..18) R.drawable.sun_cloudy else R.drawable.moon_cloudy
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

    class ShortWeatherItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            if (itemPosition != 0) outRect.left = 40
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ShortWeatherEntity>() {
            override fun areItemsTheSame(oldItem: ShortWeatherEntity, newItem: ShortWeatherEntity): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: ShortWeatherEntity, newItem: ShortWeatherEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}