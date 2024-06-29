package com.bangkit.scalesappmobile.presentatiom.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUseCase
import com.bangkit.scalesappmobile.domain.usecase.user.GetUserRoleUseCase
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.presentatiom.home.state.HomeState
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getScalesUseCase: GetScalesUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _selectedLocation = MutableStateFlow("All")
    val selectedLocation: StateFlow<String?> = _selectedLocation

    fun getUserRole(): Flow<UserRole> =
        getUserRoleUseCase().map { roleString ->
            try {
                UserRole.fromString(roleString ?: "")
            } catch (e: IllegalArgumentException) {
                UserRole.USER
            }
        }

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
