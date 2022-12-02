package com.example.java.android1.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
                setData(weatherData as Weather)
            }
        }
    }

    private fun setData(weather: Weather) {
        mBinding.currentCity.text = weather.city.city
        mBinding.currentLat.text = weather.city.lat.toString()
        mBinding.currentLon.text = weather.city.lon.toString()
        mBinding.currentTemperature.text = weather.temperature.toString()
        mBinding.currentFeelsLike.text = weather.feelsLike.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}