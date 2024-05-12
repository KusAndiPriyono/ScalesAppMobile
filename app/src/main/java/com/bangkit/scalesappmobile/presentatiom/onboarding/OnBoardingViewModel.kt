package com.bangkit.scalesappmobile.presentatiom.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.onboarding.SaveOnBoarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveOnBoarding: SaveOnBoarding,
) : ViewModel() {

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.SaveOnBoarding -> {
                saveOnBoardingEntry()
            }
        }
    }

    private fun saveOnBoardingEntry() {
        viewModelScope.launch {
            saveOnBoarding()
        }
    }
}