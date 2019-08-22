package com.koraextra.app.ui.mainActivity.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.models.NotificationModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsViewModel : KoraViewModel() {


    private var job: Job? = null
    private fun getNotificationsRepo() = Injector.getNotificationsRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var notificationsList: ArrayList<NotificationModel> = arrayListOf()

    fun getNotifiactions() {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob()
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJob(): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            when (val result = getNotificationsRepo().getNotifications()) {
                is DataResource.Success -> {
                    if (result.data.status) {
                        notificationsList.clear()
                        notificationsList.addAll(result.data.notifications)
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
                        }
                    }else{
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Empty
                        }
                    }
                }
                is DataResource.Error -> {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Error(result.exception.message!!)
                    }
                }
            }
        }
    }
}
