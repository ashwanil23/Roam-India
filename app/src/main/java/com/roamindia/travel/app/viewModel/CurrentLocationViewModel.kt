package com.roamindia.travel.app.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.roamindia.travel.app.model.CurrentLocationData

class CurrentLocationViewModel: ViewModel() {

    private val _location = mutableStateOf<CurrentLocationData?>(null)
    val location: State<CurrentLocationData?> = _location


    fun updateLocation(newLocation : CurrentLocationData){
        _location.value = newLocation
    }

}