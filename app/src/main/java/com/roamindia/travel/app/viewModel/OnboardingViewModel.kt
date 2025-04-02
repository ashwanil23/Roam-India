package com.roamindia.travel.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roamindia.travel.app.model.OnboardingData
import com.roamindia.travel.app.model.OnboardingDataRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val onboardingDataListItems: List<OnboardingData> = emptyList()
)
class OnboardingViewModel: ViewModel() {
    private val _currentStep = MutableStateFlow(0)
    var currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _onboardingData = MutableStateFlow(OnboardingUiState())
    var onboardingData: StateFlow<OnboardingUiState> = _onboardingData.asStateFlow()

    init {
        loadOnboardingData()
    }

    private fun loadOnboardingData() {
        viewModelScope.launch {
            _onboardingData.value = OnboardingUiState(
                onboardingDataListItems = OnboardingDataRes.onboardingDataList
            )
        }
    }
    fun nextStep() {
        if(_currentStep.value < (_onboardingData.value.onboardingDataListItems.size) - 1) {
            _currentStep.value++
        }
    }

    fun prevStep() {
        if(_currentStep.value > 0) {
            _currentStep.value--
        }
    }
    fun completeOnboarding() {
        Log.i("complete","onboarding complete")

    }
}