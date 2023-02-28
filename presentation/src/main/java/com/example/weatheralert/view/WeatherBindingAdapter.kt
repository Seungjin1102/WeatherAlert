package com.example.weatheralert.view

import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.MidWeatherEntity
import com.example.domain.model.ShortWeatherEntity
import com.example.weatheralert.R
import com.example.weatheralert.base.UiState
import com.example.weatheralert.base.successOrNull
import timber.log.Timber
import java.time.LocalDateTime

object WeatherBindingAdapter {

    @JvmStatic
    @BindingAdapter("isLoading")
    fun ProgressBar.isLoading(uiState: UiState<*>) {
        this.visibility = if (uiState is UiState.Loading) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isShow")
    fun View.isShow(uiState: UiState<List<ShortWeatherEntity>>) {
        this.visibility = if (uiState is UiState.Loading) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter(
        value = ["throwable", "title"],
        requireAll = true
    )
    fun View.bindDialog(throwable: Throwable?, title: String) {
        throwable?.message?.let {
            val builder = AlertDialog.Builder(context).setTitle(title).setMessage(it)
                .setCancelable(false)
                .setPositiveButton(R.string.common_ok) { p0, p1 ->
                    Timber.d("dialog ok click")
                }

            val dialog = builder.create()
            dialog.show()
        }
    }

    @JvmStatic
    @BindingAdapter("shortWeatherItem")
    fun RecyclerView.bindShortWeatherItem(uiState: UiState<List<ShortWeatherEntity>>) {
        uiState.successOrNull()?.let { data ->
            val adapter = ShortWeatherAdapter()
            this.adapter = adapter

            val set = HashSet<Int>().apply {
                add(LocalDateTime.now().dayOfMonth)
                add(LocalDateTime.now().plusDays(1).dayOfMonth)
                add(LocalDateTime.now().plusDays(2).dayOfMonth)
            }

            adapter.submitList(data.filter { set.contains(it.date.substring(6, 8).toInt()) })
            if (this.itemDecorationCount == 0) this.addItemDecoration(ShortWeatherAdapter.ShortWeatherItemDecoration())
        }
    }

    @JvmStatic
    @BindingAdapter("midWeatherItem")
    fun RecyclerView.bindMidWeatherItem(uiState: UiState<List<MidWeatherEntity>>) {
        uiState.successOrNull()?.let {
            val adapter = MidWeatherAdapter()
            this.adapter = adapter

            adapter.submitList(it)
            if (this.itemDecorationCount == 0) this.addItemDecoration(MidWeatherAdapter.MidWeatherItemDecoration())
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["time", "sky", "pty"],
        requireAll = true
    )
    fun ImageView.bindCurrentSky(time: String?, sky: String?, pty: String?) {
        if (time != null && sky != null && pty != null) {
            this.setImageResource(
                when (sky) {
                    "1" -> if ((time.toInt() / 100) in 7..18) R.drawable.sun else R.drawable.moon
                    "3" -> if ((time.toInt() / 100) in 7..18) R.drawable.sun_cloudy else R.drawable.moon_cloudy
                    else -> {
                        when (pty) {
                            "1", "4" -> R.drawable.rain
                            "2" -> R.drawable.snow
                            "3" -> R.drawable.rain_snow
                            else -> R.drawable.cloud
                        }
                    }
                }
            )
        }
    }



}