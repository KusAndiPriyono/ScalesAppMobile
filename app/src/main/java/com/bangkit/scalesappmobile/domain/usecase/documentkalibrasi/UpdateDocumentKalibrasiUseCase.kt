package com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi

import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.domain.repository.DocumentKalibrasiRepository
import javax.inject.Inject

class UpdateDocumentKalibrasiUseCase @Inject constructor(
    private val documentKalibrasiRepository: DocumentKalibrasiRepository,
) {
    suspend operator fun invoke(id: String, allForm: AllForm) =
        documentKalibrasiRepository.updateDocumentKalibrasiDetail(id, allForm)
}