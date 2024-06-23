package com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi

import com.bangkit.scalesappmobile.domain.repository.DocumentKalibrasiRepository
import javax.inject.Inject

class GetDocumentKalibrasiUseCase @Inject constructor(
    private val documentKalibrasiRepository: DocumentKalibrasiRepository,
) {
    suspend operator fun invoke() = documentKalibrasiRepository.getAllDocumentKalibrasi()
}