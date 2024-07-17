package com.bangkit.scalesappmobile.presentatiom.kalibrasi.state

import com.bangkit.scalesappmobile.domain.model.AllForm
import com.bangkit.scalesappmobile.domain.model.UpdateForm
import java.time.LocalDate

data class DocumentState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val documents: Map<LocalDate, List<AllForm>> = emptyMap(),
    val updateForm: UpdateForm? = null,
)
