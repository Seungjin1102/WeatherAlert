package com.example.weatheralert.hilt

import android.util.Log
import com.example.data.api.ApiClient
import com.example.data.api.ApiInterface
import com.example.data.api.MidWeatherInterface
import com.example.weatheralert.BuildConfig
import com.google.gson.GsonBuilder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Weather

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MidWeather

    @Provides
    fun provideWeatherApiInterface(@Weather retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    fun provideMidWeatherApiInterface(@MidWeather retrofit: Retrofit): MidWeatherInterface {
        return retrofit.create(MidWeatherInterface::class.java)
    }

    @Provides
    @Weather
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiClient.WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @MidWeather
    fun provideMidWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiClient.MID_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        LoggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(LoggerInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .build()
                proceed(newRequest)
            }
        }
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Logger.d(message)
        }.let {
            if (BuildConfig.DEBUG) {
                Logger.addLogAdapter(AndroidLogAdapter())
                it.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                it.setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }
}