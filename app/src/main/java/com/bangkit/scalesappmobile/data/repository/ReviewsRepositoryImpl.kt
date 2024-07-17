package com.bangkit.scalesappmobile.data.repository

import com.bangkit.scalesappmobile.data.remote.ScalesApiService
import com.bangkit.scalesappmobile.data.remote.scales.GetAllReviewsResponse
import com.bangkit.scalesappmobile.data.remote.scales.PostReviewsResponse
import com.bangkit.scalesappmobile.domain.model.Review
import com.bangkit.scalesappmobile.domain.repository.ReviewsRepository
import com.bangkit.scalesappmobile.util.Resource
import com.bangkit.scalesappmobile.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val scalesApiService: ScalesApiService,
) : ReviewsRepository {
    override suspend fun createReview(review: Review): Resource<PostReviewsResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.createReview(
                review = review
            )
        }
    }

    override suspend fun createReviewOnScales(
        id: String,
        review: Review,
    ): Resource<PostReviewsResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.createReviewOnScale(
                id = id,
                review = review
            )
        }
    }

    override suspend fun getReviews(): Resource<GetAllReviewsResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.getReviews()
        }
    }

    override suspend fun updateReview(id: String, review: Review): Resource<PostReviewsResponse> {
        return safeApiCall(Dispatchers.IO) {
            scalesApiService.updateReview(id = id, review = review)
        }
    }

    override suspend fun deleteReview(id: String): Resource<Boolean> {
        return safeApiCall(Dispatchers.IO) {
            val response = scalesApiService.deleteReview(id = id)
            response.toString().isNotEmpty()
        }
    }
}