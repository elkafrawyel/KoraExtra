package com.koraextra.app.ui.mainActivity.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Event
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : KoraViewModel() {

    private var job: Job? = null

    private fun registerRepo() = Injector.registerRepo()
    private fun preferencesHelper() = Injector.getPreferenceHelper()

    private var _uiState = MutableLiveData<Event<MyUiStates>>()
    val uiState: LiveData<Event<MyUiStates>>
        get() = _uiState

    fun register(body: RegisterBody) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(body)
        } else {
            _uiState.value = Event(MyUiStates.NoConnection)
        }
    }

    private fun launchJob(body: RegisterBody): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = Event(MyUiStates.Loading) }

            when (val response = registerRepo().register(body)) {
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
                            if (response.data.ar!!.name != null)
                                _uiState.value = Event(MyUiStates.Error(response.data.ar.name!!))
                            else if (response.data.ar.email != null)
                                _uiState.value = Event(MyUiStates.Error(response.data.ar.email!!))
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
