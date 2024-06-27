package com.bangkit.scalesappmobile.presentatiom.update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.UpdateDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateDocKalibrasiViewModel @Inject constructor(
    private val updateDocumentKalibrasiUseCase: UpdateDocumentKalibrasiUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _updateDocState = mutableStateOf(UpdateDocKalibrasiState())
    val updateDocState: State<UpdateDocKalibrasiState> = _updateDocState

    fun loadDocumentKalibrasi(allForm: AllForm) {
        _updateDocState.value = updateDocState.value.copy(allForm = allForm)
    }

    fun saveDocumentKalibrasi(id: String) {
        _updateDocState.value.allForm?.let { allForm ->
            updateDocumentKalibrasi(id, allForm)
        }
    }

    private fun updateDocumentKalibrasi(id: String, allForm: AllForm) {
        viewModelScope.launch {
            _updateDocState.value = updateDocState.value.copy(isLoading = true)

            when (val result = updateDocumentKalibrasiUseCase(id, allForm)) {
                is Resource.Success -> {
                    _updateDocState.value =
                        updateDocState.value.copy(isLoading = false, allForm = result.data?.data)
                    _eventFlow.emit(UiEvents.SnackbarEvent("Document updated Successfully"))

                    _eventFlow.emit(UiEvents.NavigationEvent("kalibrasi"))
                }

                is Resource.Error -> {
                    _updateDocState.value =
                        updateDocState.value.copy(isLoading = false, error = result.message)
                }

                else -> {
                    updateDocState
                }
            }
        }
    }
}

data class UpdateDocKalibrasiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val allForm: AllForm? = null,
)