package com.koraextra.app.ui.mainActivity.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.auth.LoginBody
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Event
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : KoraViewModel() {


    private var job: Job? = null

    private fun loginRepo() = Injector.loginRepo()
    private fun preferencesHelper() = Injector.getPreferenceHelper()

    private var _uiState = MutableLiveData<Event<MyUiStates>>()
    val uiState: LiveData<Event<MyUiStates>>
        get() = _uiState

    fun login(body: LoginBody) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(body)
        } else {
            _uiState.value = Event(MyUiStates.NoConnection)
        }
    }

    private fun launchJob(body: LoginBody): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = Event(MyUiStates.Loading) }

            when (val response = loginRepo().login(body)) {
                is DataResource.Success -> {
                    withContext(dispatcherProvider.main) {
                        if (response.data.status!!) {
                            preferencesHelper().id = response.data.id!!
                            preferencesHelper().name = response.data.name
                            preferencesHelper().email = response.data.email
                            preferencesHelper().token = response.data.token
                            preferencesHelper().isLoggedIn = true
                            _uiState.value = Event(MyUiStates.Success)
                        } else {
                            _uiState.value = Event(MyUiStates.Error(response.data.ar!!))
                        }
                    }
                }
                is DataResource.Error -> {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = Event(MyUiStates.Error(response.exception.message!!))
                    }
                }
            }
        }
    }
}
