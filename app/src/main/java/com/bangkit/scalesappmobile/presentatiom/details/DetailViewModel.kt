package com.bangkit.scalesappmobile.presentatiom.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scalesappmobile.data.remote.scales.GetAllReviewsResponse
import com.bangkit.scalesappmobile.data.remote.scales.PostReviewsResponse
import com.bangkit.scalesappmobile.domain.model.Review
import com.bangkit.scalesappmobile.domain.model.ScalesDetails
import com.bangkit.scalesappmobile.domain.usecase.review.CreateReviewOnScaleUseCase
import com.bangkit.scalesappmobile.domain.usecase.review.GetReviewUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.DeleteScalesUseCase
import com.bangkit.scalesappmobile.domain.usecase.scales.GetScalesDetailUseCase
import com.bangkit.scalesappmobile.domain.usecase.user.GetUserRoleUseCase
import com.bangkit.scalesappmobile.presentatiom.auth.state.TextFieldState
import com.bangkit.scalesappmobile.presentatiom.home.component.UserRole
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getScalesDetailUseCase: GetScalesDetailUseCase,
    private val deleteScalesUseCase: DeleteScalesUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    private val createReviewOnScaleUseCase: CreateReviewOnScaleUseCase
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

    private val _details = mutableStateOf(DetailState())
    val details: State<DetailState> = _details

    private val _reviewsState = mutableStateOf(ReviewState())
    val reviewsState: State<ReviewState> = _reviewsState

    private val _isDeleted = mutableStateOf(false)
    val isDeleted: State<Boolean> = _isDeleted

    private val _fillReview = mutableStateOf(TextFieldState())
    val fillReview: State<TextFieldState> = _fillReview
    fun setFillReview(value: String = "", error: String? = null) {
        _fillReview.value = fillReview.value.copy(
            text = value,
            error = error
        )
    }

    private val _rating = mutableFloatStateOf(0f)
    val rating: State<Float> = _rating
    fun setRating(value: Float) {
        _rating.floatValue = value
    }

    init {
        getReviews()
        resetReviewForm()
    }


    // In your ViewModel
    private fun resetReviewForm() {
        _fillReview.value = TextFieldState()
        _rating.floatValue = 0f
    }

    fun postReview(id: String) {
        viewModelScope.launch {
            val reviews = Review(
                createdAt = Date(),
                rating = rating.value.toInt(),
                review = fillReview.value.text,
                scale = id,
            )
            when (val result = createReviewOnScaleUseCase(id, reviews)) {
                is Resource.Success -> {
                    _reviewsState.value = reviewsState.value.copy(
                        createdReview = result.data
                    )
                    _eventsFlow.emit(UiEvents.SnackbarEvent("Review created"))
                    getReviews()
                    resetReviewForm()
                }

                is Resource.Error -> {
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                else -> {
                    reviewsState
                }
            }
        }
    }

    private fun getReviews() {
        viewModelScope.launch {
            _reviewsState.value = reviewsState.value.copy(isLoading = true)
            when (val result = getReviewUseCase()) {
                is Resource.Success -> {
                    _reviewsState.value = reviewsState.value.copy(
                        isLoading = false, reviews = result.data
                    )
                }

                is Resource.Error -> {
                    _reviewsState.value = reviewsState.value.copy(
                        isLoading = false, error = result.message
                    )
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                else -> {
                    reviewsState
                }
            }
        }
    }

    fun getDetail(id: String) {
        _details.value = details.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            when (val result = getScalesDetailUseCase(id = id)) {
                is Resource.Success -> {
                    _details.value = details.value.copy(
                        isLoading = false, scalesDetails = result.data
                    )
                }

                is Resource.Error -> {
                    _details.value = details.value.copy(
                        isLoading = false, error = result.message
                    )
                }

                else -> {
                    details
                }
            }
        }
    }

    fun deleteScales(id: String) {
        viewModelScope.launch {
            when (val result = deleteScalesUseCase(id = id)) {
                is Resource.Success -> {
                    _isDeleted.value = true
                    _eventsFlow.emit(UiEvents.SnackbarEvent("Scales deleted"))
                }

                is Resource.Error -> {
                    _eventsFlow.emit(UiEvents.SnackbarEvent(result.message ?: "An error occurred"))
                }

                else -> {
                    details
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val scalesDetails: ScalesDetails? = null,
    val isDeleted: Boolean = false,
)

data class ReviewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val reviews: GetAllReviewsResponse? = null,
    val createdReview: PostReviewsResponse? = null,
)