package com.bangkit.scalesappmobile.presentatiom.update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUpdateUseCase
import com.bangkit.scalesappmobile.presentatiom.details.DetailState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getScalesUpdateUseCase: GetScalesUpdateUseCase
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _update = mutableStateOf(DetailState())
    val update: State<DetailState> = _update

    fun updateScales(id: String) {
        _update.value = update.value.copy(
            isLoading = true
        )

        viewModelScope.launch {
            when (val result = getScalesUpdateUseCase(id = id)) {
                is Resource.Success -> {
                    _update.value = update.value.copy(
                        isLoading = false, scalesDetails = result.data
                    )
                }

                is Resource.Error -> {
                    _update.value = update.value.copy(
                        isLoading = false, error = result.message
                    )
                }

                else -> {
                    update
                }
            }
        }
    }
}