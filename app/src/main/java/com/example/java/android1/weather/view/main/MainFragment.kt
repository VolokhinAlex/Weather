package com.example.java.android1.weather.view.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.java.android1.weather.databinding.FragmentMainBinding
import com.example.java.android1.weather.model.Weather
import com.example.java.android1.weather.model.WeatherDTO
import com.example.java.android1.weather.view.Screen
import com.example.java.android1.weather.view.details.DetailsWeatherFragment
import com.example.java.android1.weather.view.details.WeatherLoader
import com.example.java.android1.weather.view.details.WeatherLoaderListener
import com.example.java.android1.weather.view.navigate
import com.example.java.android1.weather.viewmodel.AppState
import com.example.java.android1.weather.viewmodel.MainViewModel
import java.util.*

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(context = requireContext()).apply {
        setContent {
            HomeScreen(viewModel, findNavController())
        }
        //viewModel.getWeatherFromLocalSourceRus() OR INIT BLOCK IN MAIN VIEW MODEL
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val homeViewModel by viewModel.liveDataSource.observeAsState()
    val cityState = remember {
        mutableStateOf("World")
    }
    homeViewModel?.let {
        RenderDataComposable(it, navController, viewModel, cityState)
    }
}

@Composable
fun RenderDataComposable(appState: AppState, navController: NavController, viewModel: MainViewModel, cityState: MutableState<String>
) {
    when (appState) {
        is AppState.Error -> NotFoundData()
        is AppState.Success -> {
            val weatherData = appState.weatherData
            CitiesListView(weatherData, navController, viewModel, cityState)
        }
        AppState.Loading -> {
            Loading()
        }
    }
}

@Composable
fun NotFoundData() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Not found Data",
            modifier = Modifier.align(Alignment.Center), fontSize = 20.sp
        )
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Green
        )
    }
}

@Composable
private fun CitiesListView(
    weather: List<Weather>,
    navController: NavController,
    viewModel: MainViewModel,
    cityState: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(weather) { _, item ->
                Surface(modifier = Modifier.clickable {

                    item.city.let {
                        val loader = WeatherLoader(object : WeatherLoaderListener {
                            override fun onLoaded(weatherDTO: WeatherDTO) {
                                val bundle = Bundle()
                                bundle.putParcelable(DetailsWeatherFragment.ARG_WEATHER_DATA_KEY, weatherDTO)
                                navController.navigate(Screen.DetailWeatherScreen.route, bundle)
                            }

                            override fun onFailed(error: Throwable) {
                            }
                        }, it.lat, it.lon, Locale.getDefault().toString())
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            loader.loadWeather()
                        }
                    }

                    //val bundle = Bundle()
                    //bundle.putParcelable(DetailsWeatherFragment.ARG_WEATHER_DATA_KEY, item)

//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, DetailsWeatherFragment.newInstance(bundle))
//                        .addToBackStack(null).commit()
                    //navController.navigate(R.id.action_mainFragment_to_detailsWeatherFragment, bundle)


//                    navController.navigate(Screen.DetailWeatherScreen.route, bundle)
                }) {
                    CityCardView(item)
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (cityState.value == "World") {
                    viewModel.getWeatherFromLocalSourceWorld()
                    cityState.value = "Russian"
                } else {
                    viewModel.getWeatherFromLocalSourceRus()
                    cityState.value = "World"
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 5.dp, bottom = 5.dp),
            elevation = FloatingActionButtonDefaults.elevation(5.dp)
        ) {
            Text(text = cityState.value)
        }
    }
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}