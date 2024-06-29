package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.scales.CreateNewScalesUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.createscales.state.CreateScalesState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CreateScalesViewModel @Inject constructor(
    private val createNewScalesUseCase: CreateNewScalesUseCase,
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _scalesImageCover = mutableStateOf<Uri?>(null)
    val scalesImageCover: State<Uri?> = _scalesImageCover
    fun setScalesImageCover(value: Uri?) {
        _scalesImageCover.value = value
    }


    private val _scalesName = mutableStateOf(TextFieldState())
    val scalesName: State<TextFieldState> = _scalesName
    fun setScalesName(value: String = "", error: String? = null) {
        _scalesName.value = scalesName.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesBrand = mutableStateOf(TextFieldState())
    val scalesBrand: State<TextFieldState> = _scalesBrand
    fun setScalesBrand(value: String = "", error: String? = null) {
        _scalesBrand.value = scalesBrand.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesCalibrationDate = mutableStateOf(TextFieldState())
    val scalesCalibrationDate: State<TextFieldState> = _scalesCalibrationDate
    fun setScalesCalibrationDate(value: String = "", error: String? = null) {
        _scalesCalibrationDate.value = scalesCalibrationDate.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesEquipmentDescription = mutableStateOf(TextFieldState())
    val scalesEquipmentDescription: State<TextFieldState> = _scalesEquipmentDescription
    fun setScalesEquipmentDescription(value: String = "", error: String? = null) {
        _scalesEquipmentDescription.value = scalesEquipmentDescription.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesKindType = mutableStateOf(TextFieldState())
    val scalesKindType: State<TextFieldState> = _scalesKindType
    fun setScalesKindType(value: String = "", error: String? = null) {
        _scalesKindType.value = scalesKindType.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesLocation = mutableStateOf(TextFieldState())
    val scalesLocation: State<TextFieldState> = _scalesLocation
    fun setScalesLocation(value: String = "", error: String? = null) {
        _scalesLocation.value = scalesLocation.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesNextCalibrationDate = mutableStateOf(TextFieldState())
    val scalesNextCalibrationDate: State<TextFieldState> = _scalesNextCalibrationDate
    fun setScalesNextCalibrationDate(value: String = "", error: String? = null) {
        _scalesNextCalibrationDate.value = scalesNextCalibrationDate.value.copy(
            text = value,
            error = error
        )
    }


    private val _scalesCalibrationPeriod = mutableIntStateOf(0)
    val scalesCalibrationPeriod: State<Int> = _scalesCalibrationPeriod
    fun setScalesCalibrationPeriod(value: Int) {
        _scalesCalibrationPeriod.intValue = value
    }

    private val _scalesRangeCapacity = mutableIntStateOf(0)
    val scalesRangeCapacity: State<Int> = _scalesRangeCapacity
    fun setScalesRangeCapacity(value: Int) {
        _scalesRangeCapacity.intValue = value
    }

    private val _scalesParentMachineOfEquipment = mutableStateOf(TextFieldState())
    val scalesParentMachineOfEquipment: State<TextFieldState> = _scalesParentMachineOfEquipment
    fun setScalesParentMachineOfEquipment(value: String = "", error: String? = null) {
        _scalesParentMachineOfEquipment.value = scalesParentMachineOfEquipment.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesSerialNumber = mutableStateOf(TextFieldState())
    val scalesSerialNumber: State<TextFieldState> = _scalesSerialNumber
    fun setScalesSerialNumber(value: String = "", error: String? = null) {
        _scalesSerialNumber.value = scalesSerialNumber.value.copy(
            text = value,
            error = error
        )
    }

    private val _scalesUnit = mutableStateOf(TextFieldState())
    val scalesUnit: State<TextFieldState> = _scalesUnit
    fun setScalesUnit(value: String = "", error: String? = null) {
        _scalesUnit.value = scalesUnit.value.copy(
            text = value,
            error = error
        )
    }

    private val _createNewScales = mutableStateOf(CreateScalesState())
    val createNewScales: State<CreateScalesState> = _createNewScales

    fun createNewScales(
        imageCover: String,
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        equipmentDescription: String,
        kindType: String,
        location: String,
        name: String,
        nextCalibrationDate: String,
        parentMachineOfEquipment: String,
        rangeCapacity: Int,
        serialNumber: String,
        unit: String,
    ) {
        viewModelScope.launch {
            _createNewScales.value = createNewScales.value.copy(
                isLoading = true
            )

            when (
                val result = createNewScalesUseCase(
                    brand = brand,
                    calibrationDate = calibrationDate,
                    calibrationPeriod = calibrationPeriod,
                    equipmentDescription = equipmentDescription,
                    imageCover = MultipartBody.Part.createFormData(
                        "imageCover",
                        imageCover
                    ),
                    kindType = kindType,
                    location = location,
                    name = name,
                    nextCalibrationDate = nextCalibrationDate,
                    parentMachineOfEquipment = parentMachineOfEquipment,
                    rangeCapacity = rangeCapacity,
                    serialNumber = serialNumber,
                    unit = unit,
                )
            ) {
                is Resource.Error -> {
                    _createNewScales.value = createNewScales.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "An unknown error occurred"
                        )
                    )
                }

                is Resource.Success -> {
                    _createNewScales.value = createNewScales.value.copy(
                        isLoading = false,
                        uploadResponse = true
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

                else -> {
                    createNewScales
                }
            }
        }
    }


    val scalesNames = listOf(
        "Timbangan Digital",
        "T,C,Digital",
        "Pressure Gauge",
        "Box Compression Test",
        "Thickness",
        "Jangka Sorong",
        "Timbangan Besar",
        "Timbangan Kecil",
        "Metal Detektor"
    ).sorted()

    val brands = listOf(
        "AND",
        "SIEMENS",
        "ACIS",
        "YAMATO",
        "NAGATA",
        "JADEVER",
        "VIBRA",
        "T-SCALE",
        "FUJITSU",
        "DS-FOX",
        "DHS",
        "THERMOMETER",
        "EUROSICMA",
        "BOSCH",
        "REXROTH",
        "TWC",
        "KAWASHIMA",
        "OMRON",
        "TAM",
        "TWA",
        "WIKA",
        "MC",
        "ASHCROF",
        "SCHAUMBER",
        "SCHUH",
        "STEIN",
        "BROTO THERM",
    ).sorted()

    val units = listOf(
        "kg",
        "g",
        "°C/%",
        "°C",
        "Bar",
        "Mpa"
    )

}