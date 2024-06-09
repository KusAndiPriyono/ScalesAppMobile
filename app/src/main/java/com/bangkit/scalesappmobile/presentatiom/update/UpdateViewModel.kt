package com.bangkit.scalesappmobile.presentatiom.update

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesUpdateUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
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
    private val updateScalesUseCase: GetScalesUpdateUseCase,
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

    private val _updateScales = mutableStateOf(DetailState())
    val updateScales: State<DetailState> = _updateScales

    // Function to populate initial data
    fun loadScalesData(scalesDetails: ScalesDetails) {
        setScalesName(scalesDetails.name)
        setScalesBrand(scalesDetails.brand)
        setScalesCalibrationDate(scalesDetails.calibrationDate)
        setScalesEquipmentDescription(scalesDetails.equipmentDescription)
        setScalesKindType(scalesDetails.kindType)
        setScalesLocation(scalesDetails.location)
        setScalesNextCalibrationDate(scalesDetails.nextCalibrationDate)
        setScalesCalibrationPeriod(scalesDetails.calibrationPeriod)
        setScalesRangeCapacity(scalesDetails.rangeCapacity)
        setScalesParentMachineOfEquipment(scalesDetails.parentMachineOfEquipment)
        setScalesSerialNumber(scalesDetails.serialNumber)
        setScalesUnit(scalesDetails.unit)
    }

    // Function to handle the update logic
    fun saveScalesData(id: String) {
        val scalesDetails = ScalesDetails(
            name = scalesName.value.text,
            brand = scalesBrand.value.text,
            calibrationDate = scalesCalibrationDate.value.text,
            equipmentDescription = scalesEquipmentDescription.value.text,
            kindType = scalesKindType.value.text,
            location = scalesLocation.value.text,
            nextCalibrationDate = scalesNextCalibrationDate.value.text,
            calibrationPeriod = scalesCalibrationPeriod.value,
            rangeCapacity = scalesRangeCapacity.value,
            parentMachineOfEquipment = scalesParentMachineOfEquipment.value.text,
            serialNumber = scalesSerialNumber.value.text,
            unit = scalesUnit.value.text,
            imageCover = scalesImageCover.value.toString(),
            reviews = emptyList(),
            id = id,
            calibrationPeriodInYears = 0.0,
            forms = emptyList(),
            measuringEquipmentIdNumber = "",
            ratingsAverage = 5.0,
            ratingsQuantity = 5,
            status = "aktif"
        )
        updateScales(id, scalesDetails)
    }

    private fun updateScales(
        id: String,
        scalesDetails: ScalesDetails,
    ) {
        viewModelScope.launch {
            _updateScales.value = updateScales.value.copy(
                isLoading = true
            )

            when (val result = updateScalesUseCase(id, scalesDetails)) {
                is Resource.Success -> {
                    _updateScales.value = updateScales.value.copy(
                        isLoading = false, scalesDetails = result.data?.data
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Scales Update successfully"
                        )
                    )
                    _eventFlow.emit(
                        UiEvents.NavigationEvent(
                            route = ""
                        )
                    )
                }

                is Resource.Error -> {
                    _updateScales.value = updateScales.value.copy(
                        isLoading = false, error = result.message
                    )
                }

                else -> {
                    updateScales
                }
            }
        }
    }
}