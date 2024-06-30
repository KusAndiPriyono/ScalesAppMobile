package com.bangkit.scalesappmobile.presentatiom.notifications

import androidx.lifecycle.ViewModel
import com.bangkit.scalesappmobile.domain.usecase.notification.CustomWorkerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val customWorkerFactory: CustomWorkerFactory,
) : ViewModel() {


}
