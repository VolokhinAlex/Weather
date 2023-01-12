package com.example.java.android1.weather.view.details

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.java.android1.weather.databinding.FragmentDetailsWeatherBinding
import com.example.java.android1.weather.model.HoursDTO
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.model.WeatherDTO
import java.util.*

class DetailsWeatherFragment : Fragment() {

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val mBinding get() = _binding!!
    private var mWeatherData: Weather? = null
    private val TAG = DetailsWeatherFragment::class.java.toString()

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
    ) = ComposeView(context = requireActivity()).apply {
        mWeatherData = arguments?.getParcelable(ARG_WEATHER_DATA_KEY)
        mWeatherData?.city?.let {
            val loader = WeatherLoader(object : WeatherLoaderListener {

                override fun onLoaded(weatherDTO: WeatherDTO) {
                    setContent {
                        DetailsWeatherContent(weatherDTO)
                    }
                }

                override fun onFailed(error: Throwable) {
                    Log.e(TAG, error.toString())
                }

            }, it.lat, it.lon, Locale.getDefault().toString())
            loader.loadWeather()
        }
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

@Composable
fun HourlyListView(hours: List<HoursDTO>) {
    LazyRow {
        itemsIndexed(hours) { index, items ->
            HourlyWeatherItem(items)
            Log.e("", items.toString())
        }
    }
}
