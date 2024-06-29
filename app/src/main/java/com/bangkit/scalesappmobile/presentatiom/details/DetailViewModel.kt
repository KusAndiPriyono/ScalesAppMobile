package com.bangkit.scalesappmobile.presentatiom.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.usecase.scales.DeleteScalesUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesDetailUseCase
import com.bangkit.scalesappmobile.domain.usecase.user.GetUserRoleUseCase
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getScalesDetailUseCase: GetScalesDetailUseCase,
    private val deleteScalesUseCase: DeleteScalesUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    fun getUserRole(): Flow<UserRole> =
        getUserRoleUseCase().map { roleString ->
            try {
                UserRole.fromString(roleString ?: "")
            } catch (e: IllegalArgumentException) {
                UserRole.USER
            }
        }

    private val _details = mutableStateOf(DetailState())
    val details: State<DetailState> = _details

    private val _isDeleted = mutableStateOf(false)
    val isDeleted: State<Boolean> = _isDeleted

    fun getDetail(id: String) {
        _details.value = details.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            when (val result = getScalesDetailUseCase(id = id)) {
                is Resource.Success -> {
                    _details.value = details.value.copy(
                        isLoading = false, scalesDetails = result.data
                    )
                }

                is Resource.Error -> {
                    _details.value = details.value.copy(
                        isLoading = false, error = result.message
                    )
                }

                else -> {
                    details
                }
            }
        }
    }

    fun deleteScales(id: String) {
        viewModelScope.launch {
            when (val result = deleteScalesUseCase(id = id)) {
                is Resource.Success -> {
                    _isDeleted.value = true
                    _eventsFlow.emit(UiEvents.SnackbarEvent("Scales deleted"))
                }

                is Resource.Error -> {
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                else -> {
                    details
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scalesDetails: ScalesDetails? = null,
    val isDeleted: Boolean = false,
)