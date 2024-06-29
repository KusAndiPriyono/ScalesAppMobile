package com.bangkit.scalesappmobile.presentatiom.kalibrasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.DeleteDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.GetDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.domain.usecase.user.GetUserRoleUseCase
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.presentatiom.kalibrasi.state.DocumentState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ListKalibrasiViewModel @Inject constructor(
    private val getDocumentKalibrasiUseCase: GetDocumentKalibrasiUseCase,
    private val deleteDocumentKalibrasiUseCase: DeleteDocumentKalibrasiUseCase,
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

    private val _isDeleted = mutableStateOf(false)
    val isDeleted: State<Boolean> = _isDeleted

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

    fun deleteDocumentKalibrasi(id: String) {
        viewModelScope.launch {
            when (val result = deleteDocumentKalibrasiUseCase.invoke(id = id)) {
                is Resource.Success -> {
                    _isDeleted.value = true
                    _eventsFlow.emit(UiEvents.SnackbarEvent("Document deleted"))
                    getAllDocumentKalibrasi()
                }

                is Resource.Error -> {
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                else -> {
                    documentState
                }
            }
        }
    }
}