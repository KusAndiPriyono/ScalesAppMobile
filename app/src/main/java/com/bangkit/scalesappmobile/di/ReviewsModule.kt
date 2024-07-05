package com.bangkit.scalesappmobile.di

import com.bangkit.scalesappmobile.data.repository.ReviewsRepositoryImpl
import com.bangkit.scalesappmobile.domain.repository.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReviewsModule {

    @Binds
    abstract fun bindReviewsRepository(
        reviewsRepositoryImpl: ReviewsRepositoryImpl,
    ): ReviewsRepository
}