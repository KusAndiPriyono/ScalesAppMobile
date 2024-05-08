package com.bangkit.scalesappmobile.domain.usecase.scales

import com.bangkit.scalesappmobile.domain.repository.CreateScalesRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val createScalesRepository: CreateScalesRepository,
) {
    suspend operator fun invoke(
        imageCover: MultipartBody.Part,
    ) = createScalesRepository.uploadImage(
        imageCover
    )
}