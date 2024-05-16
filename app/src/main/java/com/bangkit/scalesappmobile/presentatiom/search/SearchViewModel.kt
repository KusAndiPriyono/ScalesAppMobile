package com.bangkit.scalesappmobile.presentatiom.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.domain.usecase.scales.SearchScalesUseCase
import com.bangkit.scalesappmobile.presentatiom.search.state.SearchState
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchScalesUseCase: SearchScalesUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow


    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchString = mutableStateOf("")
    val searchString: State<String> = _searchString
    fun setSearchString(value: String) {
        _searchString.value = value
        _searchState.value = searchState.value.copy(error = null, scales = emptyFlow())
    }

    fun search(brand: String) {
        viewModelScope.launch {
            if (brand.isBlank()) {
                Timber.d("String Pencarian Kosong")
                _eventFlow.emit(UiEvents.SnackbarEvent(message = "Mohon isi kolom pencarian"))
                return@launch
            }
            _searchState.value = searchState.value.copy(isLoading = true)

            when (
                val result = searchScalesUseCase(
                    brand = listOf(brand),
                )
            ) {
                is Resource.Success -> {
                    _searchState.value = searchState.value.copy(
                        isLoading = false,
                        scales = result.data ?: emptyFlow(),
                    )
                }

                is Resource.Error -> {
                    _searchState.value = searchState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Terjadi kesalahan"
                    )
                }

                else -> {
                    searchState
                }
            }
        }
    }
}