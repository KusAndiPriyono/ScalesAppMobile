package com.bangkit.scalesappmobile.presentatiom.createscales

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.scales.CreateNewScalesUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.UploadImageUseCase
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
    private val uploadImageUseCase: UploadImageUseCase,
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
        imageCover: MultipartBody.Part,
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
                val uploadResult = uploadImageUseCase(
                    imageCover = imageCover
                )
            ) {
                is Resource.Error -> {
                    _createNewScales.value = createNewScales.value.copy(
                        isLoading = false,
                        error = uploadResult.message
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = uploadResult.message ?: "An unknown error occurred"
                        )
                    )
                }

                is Resource.Success -> {
                    saveScales(
                        brand = brand,
                        calibrationDate = calibrationDate,
                        calibrationPeriod = calibrationPeriod,
                        equipmentDescription = equipmentDescription,
                        imageCover = uploadResult.data.toString(),
                        kindType = kindType,
                        location = location,
                        name = name,
                        nextCalibrationDate = nextCalibrationDate,
                        parentMachineOfEquipment = parentMachineOfEquipment,
                        rangeCapacity = rangeCapacity,
                        serialNumber = serialNumber,
                        unit = unit,
                    )
                }

                else -> {}
            }
        }
    }

    private fun saveScales(
        brand: String,
        calibrationDate: String,
        calibrationPeriod: Int,
        equipmentDescription: String,
        imageCover: String,
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
            when (
                val result = createNewScalesUseCase(
                    brand = brand,
                    calibrationDate = calibrationDate,
                    calibrationPeriod = calibrationPeriod,
                    equipmentDescription = equipmentDescription,
                    imageCover = imageCover,
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
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "An unknown error occurred"
                        )
                    )
                }

                is Resource.Success -> {
                    _createNewScales.value = createNewScales.value.copy(
                        isLoading = false,
                        scalesIsSaved = result.data ?: error("Data is null")
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

                else -> {}
            }
        }
    }

//    fun createScales() {
//        viewModelScope.launch {
//            if (scalesName.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the scales name")
//                )
//                return@launch
//            }
//
//            if (scalesBrand.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the scales brand")
//                )
//                return@launch
//            }
//
//            if (scalesCalibrationDate.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the calibration date")
//                )
//                return@launch
//            }
//
//            if (scalesCalibrationPeriod.value == 0) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the calibration period")
//                )
//                return@launch
//            }
//
//            if (scalesRangeCapacity.value == 0) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the range capacity")
//                )
//                return@launch
//            }
//
//            if (scalesEquipmentDescription.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the equipment description")
//                )
//                return@launch
//            }
//
//            if (scalesKindType.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the kind type")
//                )
//                return@launch
//            }
//
//            if (scalesLocation.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the location")
//                )
//                return@launch
//            }
//
//            if (scalesNextCalibrationDate.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the next calibration date")
//                )
//                return@launch
//            }
//
//            if (scalesParentMachineOfEquipment.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the parent machine of equipment")
//                )
//                return@launch
//            }
//
//            if (scalesSerialNumber.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the serial number")
//                )
//                return@launch
//            }
//
//            if (scalesUnit.value.text.isEmpty()) {
//                _eventFlow.emit(
//                    UiEvents.SnackbarEvent(message = "Please fill in the unit")
//                )
//                return@launch
//            }
//
//            _createScaleState.value = CreateScalesState(isLoading = true)
//
//            when (
//                val result = createNewScalesUseCase(
//                    brand = scalesBrand.value.text,
//                    calibrationDate = scalesCalibrationDate.value.text,
//                    calibrationPeriod = scalesCalibrationPeriod.value,
//                    equipmentDescription = scalesEquipmentDescription.value.text,
//                    imageCover = "",
//                    kindType = scalesKindType.value.text,
//                    location = scalesLocation.value.text,
//                    name = scalesName.value.text,
//                    nextCalibrationDate = scalesNextCalibrationDate.value.text,
//                    parentMachineOfEquipment = scalesParentMachineOfEquipment.value.text,
//                    rangeCapacity = scalesRangeCapacity.value,
//                    serialNumber = scalesSerialNumber.value.text,
//                    unit = scalesUnit.value.text,
//                )
//            ) {
//                is Resource.Error -> {
//                    _createScaleState.value = CreateScalesState(
//                        isLoading = false,
//                        error = result.message ?: "An unknown error occurred"
//                    )
//                    _eventFlow.emit(
//                        UiEvents.SnackbarEvent(
//                            message = result.message ?: "An unknown error occurred"
//                        )
//                    )
//                }
//
//                is Resource.Success -> {
//                    _createScaleState.value = _createScaleState.value.copy(
//                        isLoading = false,
//                        scalesIsSaved = result.data ?: error("Data is null")
//                    )
//                    _eventFlow.emit(
//                        UiEvents.SnackbarEvent(
//                            message = "Scales created successfully"
//                        )
//                    )
//                    _eventFlow.emit(UiEvents.NavigationEvent(HomeScreenDestination.route))
//                }
//
//                else -> {
//                    createScaleState
//                }
//            }
//        }
//    }
}