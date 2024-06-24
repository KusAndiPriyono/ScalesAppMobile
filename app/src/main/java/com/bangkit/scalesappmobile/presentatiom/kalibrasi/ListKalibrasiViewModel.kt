package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.GetDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.state.DocumentState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ListKalibrasiViewModel @Inject constructor(
    private val getDocumentKalibrasiUseCase: GetDocumentKalibrasiUseCase,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val _documentState = mutableStateOf(DocumentState())
    val documentState: State<DocumentState> = _documentState

    private val _selectedStatusApproval = MutableStateFlow("All")
    val selectedStatusApproval: StateFlow<String> = _selectedStatusApproval

    fun setSelectedStatusApproval(statusApproval: String) {
        _selectedStatusApproval.value = statusApproval
    }

    init {
        getAllDocumentKalibrasi()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllDocumentKalibrasi() {
        viewModelScope.launch {
            _documentState.value = documentState.value.copy(isLoading = true)
            when (val result = getDocumentKalibrasiUseCase.invoke()) {
                is Resource.Success -> {
                    val documentsMap = result.data?.groupBy {
                        it.createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    _documentState.value =
                        documentState.value.copy(
                            documents = documentsMap ?: emptyMap(),
                            isLoading = false
                        )
                }

                is Resource.Error -> {
                    _documentState.value =
                        documentState.value.copy(error = result.message, isLoading = false)
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message.toString()))
                }

                else -> {
                    _documentState.value = documentState.value.copy(isLoading = false)
                }
            }
        }
    }
}