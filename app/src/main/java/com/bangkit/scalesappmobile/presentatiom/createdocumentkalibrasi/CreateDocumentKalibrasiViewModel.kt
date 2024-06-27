package com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.Form
import com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi.CreateFormDocumentKalibrasiUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi.state.CreateDocumentKalibrasiState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateDocumentKalibrasiViewModel @Inject constructor(
    private val createFormDocumentKalibrasiUseCase: CreateFormDocumentKalibrasiUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _calibrationMethod = mutableStateOf(TextFieldState())
    val calibrationMethod: State<TextFieldState> = _calibrationMethod
    fun setCalibrationMethod(value: String = "", error: String? = null) {
        _calibrationMethod.value = calibrationMethod.value.copy(
            text = value,
            error = error
        )
    }

    private val _reference = mutableStateOf(TextFieldState())
    val reference: State<TextFieldState> = _reference
    fun setReference(value: String = "", error: String? = null) {
        _reference.value = reference.value.copy(
            text = value,
            error = error
        )
    }

    private val _standardCalibration = mutableStateOf(TextFieldState())
    val standardCalibration: State<TextFieldState> = _standardCalibration
    fun setStandardCalibration(value: String = "", error: String? = null) {
        _standardCalibration.value = standardCalibration.value.copy(
            text = value,
            error = error
        )
    }

    private val _suhu = mutableIntStateOf(0)
    val suhu: State<Int> = _suhu
    fun setSuhu(value: Int) {
        _suhu.intValue = value
    }

    private val _resultCalibration = mutableStateOf(TextFieldState())
    val resultCalibration: State<TextFieldState> = _resultCalibration
    fun setResultCalibration(value: String = "", error: String? = null) {
        _resultCalibration.value = resultCalibration.value.copy(
            text = value,
            error = error
        )
    }

    private val _validUntil = mutableStateOf(TextFieldState())
    val validUntil: State<TextFieldState> = _validUntil
    fun setValidUntil(value: String = "", error: String? = null) {
        _validUntil.value = validUntil.value.copy(
            text = value,
            error = error
        )
    }

    private val _readingCenter = mutableStateOf(TextFieldState())
    val readingCenter: State<TextFieldState> = _readingCenter
    fun setReadingCenter(value: Double = 0.0, error: String? = null) {
        _readingCenter.value = readingCenter.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
        updateMaxTotalReading()
    }
    //        _readingCenter.doubleValue = value
//        updateMaxTotalReading()

    private val _readingFront = mutableStateOf(TextFieldState())
    val readingFront: State<TextFieldState> = _readingFront
    fun setReadingFront(value: Double = 0.0, error: String? = null) {
        _readingFront.value = readingFront.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
        updateMaxTotalReading()
    }

    private val _readingBack = mutableStateOf(TextFieldState())
    val readingBack: State<TextFieldState> = _readingBack
    fun setReadingBack(value: Double = 0.0, error: String? = null) {
        _readingBack.value = readingBack.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
        updateMaxTotalReading()
    }

    private val _readingLeft = mutableStateOf(TextFieldState())
    val readingLeft: State<TextFieldState> = _readingLeft
    fun setReadingLeft(value: Double = 0.0, error: String? = null) {
        _readingLeft.value = readingLeft.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
        updateMaxTotalReading()
    }

    private val _readingRight = mutableStateOf(TextFieldState())
    val readingRight: State<TextFieldState> = _readingRight
    fun setReadingRight(value: Double = 0.0, error: String? = null) {
        _readingRight.value = readingRight.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
        updateMaxTotalReading()
    }

    private val _maxTotalReading = mutableStateOf(TextFieldState())
    val maxTotalReading: State<TextFieldState> = _maxTotalReading
    fun setMaxTotalReading(value: Double = 0.0, error: String? = null) {
        _maxTotalReading.value = maxTotalReading.value.copy(
            outputNumberScale = value.toInt(),
            error = error
        )
    }

    private val _createDocumentKalibrasi = mutableStateOf(CreateDocumentKalibrasiState())
    val createDocumentKalibrasi: State<CreateDocumentKalibrasiState> = _createDocumentKalibrasi

    private fun updateMaxTotalReading() {
        val readings = listOf(
            _readingCenter.value.outputNumberScale.toDouble(),
            _readingFront.value.outputNumberScale.toDouble(),
            _readingBack.value.outputNumberScale.toDouble(),
            _readingLeft.value.outputNumberScale.toDouble(),
            _readingRight.value.outputNumberScale.toDouble()
        )
        val maxReading = readings.maxOrNull() ?: 0.0
        val minReading = readings.minOrNull() ?: 0.0

        val totalReading = maxReading - minReading
        _maxTotalReading.value = maxTotalReading.value.copy(
            outputNumberScale = totalReading.toInt()
        )
    }

    fun createDocumentKalibrasi(id: String) {
        val form = Form(
            calibrationMethod = calibrationMethod.value.text,
            createdAt = Date(),
            reference = reference.value.text,
            resultCalibration = resultCalibration.value.text,
            scale = id,
            standardCalibration = standardCalibration.value.text,
            suhu = suhu.value,
            validUntil = validUntil.value.text,
            readingCenter = readingCenter.value.outputNumberScale.toDouble(),
            readingFront = readingFront.value.outputNumberScale.toDouble(),
            readingBack = readingBack.value.outputNumberScale.toDouble(),
            readingLeft = readingLeft.value.outputNumberScale.toDouble(),
            readingRight = readingRight.value.outputNumberScale.toDouble(),
            maxTotalReading = maxTotalReading.value.outputNumberScale.toDouble()
        )

        viewModelScope.launch {
            _createDocumentKalibrasi.value = CreateDocumentKalibrasiState(isLoading = true)
            when (val result = createFormDocumentKalibrasiUseCase(form)) {
                is Resource.Success -> {
                    _createDocumentKalibrasi.value = createDocumentKalibrasi.value.copy(
                        isLoading = false,
                        createFormDocumentKalibrasi = result.data
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Scales created successfully"
                        )
                    )
                    _eventFlow.emit(
                        UiEvents.NavigationEvent(
                            route = ""
                        )
                    )
                }

                is Resource.Error -> {
                    _createDocumentKalibrasi.value = createDocumentKalibrasi.value.copy(
                        isLoading = false,
                        error = result.message ?: "Terjadi kesalahan"
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Terjadi kesalahan"
                        )
                    )
                }

                else -> {
                    createDocumentKalibrasi
                }
            }
        }
    }
}