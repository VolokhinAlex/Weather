package com.example.java.android1.weather.view.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.databinding.FragmentDetailsWeatherBinding
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.model.WeatherDTO
import java.util.*

class DetailsWeatherFragment : Fragment() {

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val mBinding get() = _binding!!
    private var mWeatherData: Weather? = null
    private val TAG = DetailsWeatherFragment::class.java.toString()

    private val listener = object : WeatherLoaderListener {
        override fun onLoaded(weatherDTO: WeatherDTO) {
            displayWeather(weatherDTO)
            setData(weatherDTO)
        }

        override fun onFailed(error: Throwable) {
            Log.e(TAG, error.toString())
        }
    }

    private val hourlyWeatherAdapter = HourlyWeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mWeatherData = it.getParcelable(ARG_WEATHER_DATA_KEY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsWeatherBinding.inflate(inflater, container, false)
        mWeatherData = arguments?.getParcelable(ARG_WEATHER_DATA_KEY)
        val recyclerView: RecyclerView = mBinding.containerHourlyWeather
        recyclerView.adapter = hourlyWeatherAdapter
        val layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        mWeatherData?.city?.let {
            val loader = WeatherLoader(listener, it.lat, it.lon, Locale.getDefault().toString())
            loader.loadWeather()
        }
        return mBinding.root
    }

    /**
     * The method is for setting data in the UI
     */

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun displayWeather(weatherDTO: WeatherDTO) {
        val date = Date(weatherDTO.now * 1000L)
        val day = DateFormat.format("EEEE", date)
        val month = DateFormat.format("MMMM", date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        with(mBinding) {
            currentCity.text = "${weatherDTO.geo_object?.locality?.name}, "
            currentCountry.text = weatherDTO.geo_object?.country?.name
            currentDay.text = day
            currentDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)} ${month}, "
            currentWindySpeed.text = String.format("%s m/s", weatherDTO.fact?.wind_speed)
            currentHumidity.text = String.format("%s %%", weatherDTO.fact?.humidity)
            currentPressure.text = String.format("%s mb", weatherDTO.fact?.pressure_mm)
            currentTemperature.text = String.format("%s °", weatherDTO.fact?.temp)
            currentFeelsLike.text = String.format("%s °", weatherDTO.fact?.feels_like)
            currentCondition.text = weatherDTO.fact?.condition
            currentSunrise.text = weatherDTO.forecasts?.get(0)?.sunrise
            currentSunset.text = weatherDTO.forecasts?.get(0)?.sunset
        }
    }

    /**
     * The method sets the hourly weather forecast data for the list in the Adapter
     */

    private fun setData(weatherDTO: WeatherDTO) {
        weatherDTO.forecasts?.get(0)?.hours?.let { hourlyWeatherAdapter.setData(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_WEATHER_DATA_KEY = "weather_data_key"

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            DetailsWeatherFragment().apply {
                arguments = bundle
            }
    }
}
