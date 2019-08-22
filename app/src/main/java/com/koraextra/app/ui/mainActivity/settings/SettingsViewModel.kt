package com.koraextra.app.ui.mainActivity.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : KoraViewModel() {

    //==============================================================
    private var job: Job? = null

    private fun getSettingRepo() = Injector.settingRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState
    var message: String = "."

    fun setting(name: String, status: Int) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(name, status)
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJob(name: String, status: Int): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            val result = getSettingRepo().setting(name, status)

            when (result) {
                is DataResource.Success -> {
                    if (result.data.status) {
                        message=result.data.ar
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
                        }

                    } else {
                        //NO Matches Stored
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Error(result.data.ar)
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

