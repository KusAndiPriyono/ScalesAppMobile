package com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi

import androidx.lifecycle.ViewModel
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.CreateFormDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class CreateDocumentKalibrasiViewModel @Inject constructor(
    private val createFormDocumentKalibrasiUseCase: CreateFormDocumentKalibrasiUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
}