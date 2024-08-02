package com.bangkit.scalesappmobile.presentatiom.laporan

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.Scales
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesGrafisUseCase
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaporanGrafisViewModel @Inject constructor(
    private val getScalesGrafisUseCase: GetScalesGrafisUseCase
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _scalesGrafisState = mutableStateOf(GrafisState())
    val scalesGrafisState: State<GrafisState> = _scalesGrafisState

    private val _totalItems = mutableIntStateOf(GrafisState().data.size)
    val totalItems: State<Int> = _totalItems

    init {
        getScalesGrafis()
    }

    private fun getScalesGrafis() {
        viewModelScope.launch {
            _scalesGrafisState.value = GrafisState(isLoading = true)
            when (val result = getScalesGrafisUseCase()) {
                is Resource.Success -> {
                    val scalesList = result.data ?: emptyList()
                    _scalesGrafisState.value = scalesGrafisState.value.copy(
                        isLoading = false,
                        data = scalesList
                    )
                    _totalItems.intValue = scalesList.size
                }

                is Resource.Error -> {
                    _scalesGrafisState.value =
                        GrafisState(error = result.message ?: "An unexpected error occurred")
                }

                else -> {
                    _scalesGrafisState
                }
            }
        }
    }
}

data class GrafisState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<Scales> = emptyList()
)