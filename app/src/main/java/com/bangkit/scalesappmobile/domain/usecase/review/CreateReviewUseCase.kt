package com.bangkit.scalesappmobile.domain.usecase.review

import com.bangkit.scalesappmobile.domain.repository.ReviewsRepository
import javax.inject.Inject

class CreateReviewUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
) {
//    suspend operator fun invoke(review: Review) =
//        reviewsRepository.createReview(review)
}