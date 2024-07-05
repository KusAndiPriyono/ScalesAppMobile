package com.bangkit.scalesappmobile.domain.usecase.review

import com.bangkit.scalesappmobile.domain.model.Review
import com.bangkit.scalesappmobile.domain.repository.ReviewsRepository
import javax.inject.Inject

class UpdateReviewUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
) {
    suspend operator fun invoke(id: String, review: Review) =
        reviewsRepository.updateReview(id, review)
}
