package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.scales.GetLocationUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUseCase
import com.bangkit.scalesappmobile.presentatiom.home.state.HomeState
import com.bangkit.scalesappmobile.presentatiom.home.state.LocationsState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScalesUseCase: GetScalesUseCase,
    private val getLocationsUseCase: GetLocationUseCase,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _selectedLocation = mutableStateOf("All")
    val selectedLocation: State<String> = _selectedLocation

    fun setSelectedLocation(value: String) {
        _selectedLocation.value = value
    }

    var state = mutableStateOf(HomeState())
        private set

    private val _locations = mutableStateOf(LocationsState())
    val locations: State<LocationsState> = _locations

    private val _scalesState = mutableStateOf(HomeState())
    val scalesState: State<HomeState> = _scalesState

    init {
        getLocations()
        getScales(location = selectedLocation.value)
    }

    private fun getLocations() {
        _locations.value = locations.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = getLocationsUseCase()) {
                is Resource.Error -> {
                    _locations.value = locations.value.copy(
                        isLoading = false,
                        error = result.message,
                        locations = result.data ?: emptyList()
                    )
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                is Resource.Success -> {
                    _locations.value = locations.value.copy(
                        isLoading = false,
                        locations = result.data ?: emptyList()
//                        locations = listOf(
//                            Location(
//                                id = "id",
//                                location = "All",
//                            )
//                        ) + (result.data ?: emptyList())
                    )
                }

                else -> {
                    locations
                }
            }

        }
    }

//    fun getScales(location: String): Flow<PagingData<Scales>> =
//        getScalesUseCase.invoke(location).cachedIn(viewModelScope)

    fun getScales(location: String) {
        _scalesState.value = scalesState.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = getScalesUseCase(location = location)) {
                is Resource.Error -> {
                    _scalesState.value = scalesState.value.copy(
                        isLoading = false,
                        error = result.message,
                        scales = result.data ?: emptyList()
                    )
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                is Resource.Success -> {
                    _scalesState.value = scalesState.value.copy(
                        isLoading = false,
                        scales = result.data ?: emptyList()
                    )
                }

                else -> {
                    scalesState
                }
            }
        }
    }

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
