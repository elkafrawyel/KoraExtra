package com.koraextra.app.ui.mainActivity.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchViewModel : KoraViewModel() {

    var fixtureId: Int? = null
    //==============================================================
    private var job: Job? = null

    private fun getStoredMatches() = Injector.getStoredMatchesRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var matchLiveData: LiveData<MatchModel>? = null
    var match: MatchModel? = null

    fun getMatch() {
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
            when (val databaseResult = getStoredMatches().getStoredMatchById(fixtureId!!)) {
                is DataResource.Success -> {
                    matchLiveData = databaseResult.data
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Success
                    }
                }
                is DataResource.Error -> {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Error(databaseResult.exception.message!!)
                    }
                }
            }
        }
    }
}
