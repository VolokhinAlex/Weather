package com.example.java.android1.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.databinding.FragmentMainBinding
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.viewmodel.AppState
import com.example.java.android1.weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromLocalSource()
        val recyclerView: RecyclerView = mBinding.containerHourlyWeather
        val hourlyWeatherAdapter = HourlyWeatherAdapter(layoutInflater)
        recyclerView.adapter = hourlyWeatherAdapter
        val layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.layoutManager = layoutManager
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                val weatherData = appState.error
                mBinding.progressBar.visibility = View.GONE
                mBinding.errorMessage.visibility = View.VISIBLE
                mBinding.errorMessage.text = weatherData.message
            }
            AppState.Loading -> {
                mBinding.progressBar.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                mBinding.progressBar.visibility = View.GONE
                setData(weatherData)
            }
        }
    }

    private fun setData(weather: Weather) {
        mBinding.currentCity.text = weather.city.city
        mBinding.currentLat.text = weather.city.lat.toString()
        mBinding.currentLon.text = weather.city.lon.toString()
        mBinding.currentTemperature.text = String.format("%s °", weather.temperature)
        mBinding.currentFeelsLike.text = String.format("%s °", weather.feelsLike)
        mBinding.currentCondition.text = weather.condition
        mBinding.currentWindySpeed.text = String.format("%s m/s", weather.windSpeed)
        mBinding.currentHumidity.text = String.format("%s %%", weather.humidity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}