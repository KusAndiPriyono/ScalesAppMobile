package com.bangkit.scalesappmobile.domain.usecase.documentkalibrasi

import com.bangkit.scalesappmobile.domain.model.Form
import com.bangkit.scalesappmobile.domain.repository.CreateFormDocumentKalibrasiRepository
import javax.inject.Inject

class CreateFormDocumentKalibrasiUseCase @Inject constructor(
    private val createFormDocumentKalibrasiRepository: CreateFormDocumentKalibrasiRepository,
) {
    suspend operator fun invoke(formKalibrasi: Form) =
        createFormDocumentKalibrasiRepository.createFormDocumentKalibrasi(formKalibrasi)

}