package com.bangkit.scalesappmobile.presentatiom.createdocumentkalibrasi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
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

    private val _readingCenter = mutableDoubleStateOf(0.0)
    val readingCenter: State<Double> = _readingCenter
    fun setReadingCenter(value: Double) {
        _readingCenter.doubleValue = value
        updateMaxTotalReading()
    }

    private val _readingFront = mutableDoubleStateOf(0.0)
    val readingFront: State<Double> = _readingFront
    fun setReadingFront(value: Double) {
        _readingFront.doubleValue = value
        updateMaxTotalReading()
    }

    private val _readingBack = mutableDoubleStateOf(0.0)
    val readingBack: State<Double> = _readingBack
    fun setReadingBack(value: Double) {
        _readingBack.doubleValue = value
        updateMaxTotalReading()
    }

    private val _readingLeft = mutableDoubleStateOf(0.0)
    val readingLeft: State<Double> = _readingLeft
    fun setReadingLeft(value: Double) {
        _readingLeft.doubleValue = value
        updateMaxTotalReading()
    }

    private val _readingRight = mutableDoubleStateOf(0.0)
    val readingRight: State<Double> = _readingRight
    fun setReadingRight(value: Double) {
        _readingRight.doubleValue = value
        updateMaxTotalReading()
    }

    private val _maxTotalReading = mutableDoubleStateOf(0.0)
    val maxTotalReading: State<Double> = _maxTotalReading
    fun setMaxTotalReading(value: Double) {
        _maxTotalReading.doubleValue = value
    }

    private val _createDocumentKalibrasi = mutableStateOf(CreateDocumentKalibrasiState())
    val createDocumentKalibrasi: State<CreateDocumentKalibrasiState> = _createDocumentKalibrasi

    private fun updateMaxTotalReading() {
        val readings = listOf(
            _readingCenter.doubleValue,
            _readingFront.doubleValue,
            _readingBack.doubleValue,
            _readingLeft.doubleValue,
            _readingRight.doubleValue
        )
        val maxReading = readings.maxOrNull() ?: 0.0
        val minReading = readings.minOrNull() ?: 0.0
        _maxTotalReading.doubleValue = maxReading - minReading
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
            readingCenter = readingCenter.value.toInt(),
            readingFront = readingFront.value.toInt(),
            readingBack = readingBack.value.toInt(),
            readingLeft = readingLeft.value.toInt(),
            readingRight = readingRight.value.toInt(),
            maxTotalReading = maxTotalReading.value.toInt(),
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