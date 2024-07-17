package com.bangkit.scalesappmobile.domain.repository

import com.bangkit.scalesappmobile.data.remote.scales.GetAllReviewsResponse
import com.bangkit.scalesappmobile.data.remote.scales.PostReviewsResponse
import com.bangkit.scalesappmobile.domain.model.Review
import com.bangkit.scalesappmobile.util.Resource

interface ReviewsRepository {

    suspend fun createReview(
        review: Review,
    ): Resource<PostReviewsResponse>

    suspend fun createReviewOnScales(
        id: String,
        review: Review,
    ): Resource<PostReviewsResponse>

    suspend fun getReviews(): Resource<GetAllReviewsResponse>

    suspend fun updateReview(
        id: String,
        review: Review,
    ): Resource<PostReviewsResponse>

    suspend fun deleteReview(id: String): Resource<Boolean>
}