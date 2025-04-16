package com.roamindia.travel.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamindia.travel.app.weatherApi.Constant
import com.roamindia.travel.app.weatherApi.NetworkResponse
import com.roamindia.travel.app.weatherApi.RetrofitInstance
import com.roamindia.travel.app.weatherApi.model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    public val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult
    fun getWeatherData(place: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey,place,true)
                if(response.isSuccessful) {
                    Log.i("Response",response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else {
                    _weatherResult.value = NetworkResponse.Error("Failed to Load Data")
                }
            }catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to Load Data")
                Log.i("Error", "Error fetching data: ${e.message}")
            }
        }
    }
}