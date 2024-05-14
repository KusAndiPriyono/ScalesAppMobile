package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUseCase
import com.bangkit.scalesappmobile.presentatiom.home.state.HomeState
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScalesUseCase: GetScalesUseCase,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _selectedLocation = MutableStateFlow<String?>(null)
    val selectedLocation: StateFlow<String?> = _selectedLocation


    fun setSelectedLocation(location: String) {
        _selectedLocation.value = location
    }

    var state = mutableStateOf(HomeState())
        private set

    fun getScales(): Flow<PagingData<Scales>> =
        getScalesUseCase.invoke().cachedIn(viewModelScope)

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }

    private fun updateScrollValue(newValue: Int) {
        state.value = state.value.copy(scrollValue = newValue)
    }

    private fun updateMaxScrollingValue(newValue: Int) {
        state.value = state.value.copy(maxScrollingValue = newValue)
    }
}
