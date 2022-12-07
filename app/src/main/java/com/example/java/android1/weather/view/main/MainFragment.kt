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

    private lateinit var viewModel: MainViewModel
    private lateinit var mMainAdapter: MainFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromLocalSourceRus()
        mBinding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        val recyclerView: RecyclerView = mBinding.containerListOfCities
        mMainAdapter = MainFragmentAdapter(object : OnItemClickListener {
            override fun onItemClickListener(weather: Weather) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailsWeatherFragment.newInstance(weather))
                    .addToBackStack(null).commit()
            }
        })
        recyclerView.adapter = mMainAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layoutManager
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
                mMainAdapter.setWeather(weatherData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}