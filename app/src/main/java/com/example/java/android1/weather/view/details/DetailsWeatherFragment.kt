package com.example.java.android1.weather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.databinding.FragmentDetailsWeatherBinding
import com.example.java.android1.weather.model.Weather

private const val ARG_WEATHER_DATA_KEY = "weather_data_key"

class DetailsWeatherFragment : Fragment() {

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val mBinding get() = _binding!!
    private var mWeatherData: Weather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mWeatherData = it.getParcelable(ARG_WEATHER_DATA_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsWeatherBinding.inflate(inflater, container, false)
        mWeatherData = arguments?.getParcelable(ARG_WEATHER_DATA_KEY)
        val recyclerView: RecyclerView = mBinding.containerHourlyWeather
        val hourlyWeatherAdapter = HourlyWeatherAdapter(layoutInflater)
        recyclerView.adapter = hourlyWeatherAdapter
        val layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        setData(mWeatherData)
        return mBinding.root
    }

    private fun setData(weather: Weather?) {
        mBinding.currentCity.text = weather?.city?.city
        mBinding.currentTemperature.text = String.format("%s °", weather?.temperature)
        mBinding.currentFeelsLike.text = String.format("%s °", weather?.feelsLike)
        mBinding.currentCondition.text = weather?.condition
        mBinding.currentWindySpeed.text = String.format("%s m/s", weather?.windSpeed)
        mBinding.currentHumidity.text = String.format("%s %%", weather?.humidity)
        mBinding.currentPressure.text = String.format("%s mb", weather?.pressure)
        mBinding.currentSunrise.text = weather?.sunrise
        mBinding.currentSunset.text = weather?.sunset
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(weather: Weather) =
            DetailsWeatherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_WEATHER_DATA_KEY, weather)
                }
            }
    }
}