package com.example.java.android1.weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.weather.R
import com.example.java.android1.weather.databinding.FragmentMainBinding
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.view.details.DetailsWeatherFragment
import com.example.java.android1.weather.viewmodel.AppState
import com.example.java.android1.weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private var isDataSetRus: Boolean = true

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var mMainAdapter: MainFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.apply {
            this.liveDataSource.observe(viewLifecycleOwner) { renderData(it) }
            this.getWeatherFromLocalSourceRus()
        }
        mBinding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        val recyclerView: RecyclerView = mBinding.containerListOfCities
        mMainAdapter = MainFragmentAdapter { weatherData ->
            val bundle = Bundle()
            bundle.putParcelable(DetailsWeatherFragment.ARG_WEATHER_DATA_KEY, weatherData)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsWeatherFragment.newInstance(bundle))
                .addToBackStack(null).commit()
        }
        recyclerView.apply {
            this.adapter = mMainAdapter
            this.layoutManager = LinearLayoutManager(requireActivity())
        }
        return mBinding.root
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            mBinding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            mBinding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                val weatherData = appState.error
                mBinding.progressBar.makeGone()
                mBinding.errorMessage.makeVisible()
                mBinding.errorMessage.text = weatherData.message
            }
            AppState.Loading -> {
                mBinding.progressBar.makeVisible()
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                mBinding.progressBar.makeGone()
                mMainAdapter.setWeather(weatherData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMainAdapter.removeListener()
        _binding = null
    }

}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}