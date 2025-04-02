package com.roamindia.travel.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel

class PlaceSearchViewModel: ViewModel() {
    fun getPlaceData(place: String) {
        Log.i("Place name",place)
    }
}