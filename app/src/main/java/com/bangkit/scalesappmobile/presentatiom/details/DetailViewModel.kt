package com.bangkit.scalesappmobile.presentatiom.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesDetailUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUpdateUseCase
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getScalesDetailUseCase: GetScalesDetailUseCase,
    private val getScalesUpdateUseCase: GetScalesUpdateUseCase
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _details = mutableStateOf(DetailState())
    val details: State<DetailState> = _details

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
}

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scalesDetails: ScalesDetails? = null
)