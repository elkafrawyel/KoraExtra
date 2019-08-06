package com.koraextra.app.ui.mainActivity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.Injector
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : KoraViewModel() {

    var opened: Boolean = false
    var allowSwitch: Boolean = true

    //==============================================================
    private var job: Job? = null
    private fun getMatchesRepo() = Injector.getMatchesRepo()
    private fun getStoredMatches() = Injector.getStoredMatchesRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var storedMatchesLiveData: LiveData<List<MatchModel>>? = null
    var date: String? = null
    var matchesList: ArrayList<MatchModel> = arrayListOf()

    fun getMatchesList(isLive: Boolean = false) {
//        _uiState.value = MyUiStates.NoConnection
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(isLive)
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJob(isLive: Boolean): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            var result: DataResource<Boolean>? = null
            if (isLive) {
                result = getMatchesRepo().getMatches(getLiveMatches())
            } else {
                result = getMatchesRepo().getMatches(getMatchesOfDate(date!!))
            }

            when (result) {
                is DataResource.Success -> {
                    if (result.data) {

                        when (val databaseResult = getStoredMatches().getStoredMatches("")) {
                            is DataResource.Success -> {
                                storedMatchesLiveData = databaseResult.data
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
                    } else {
                        //NO Matches Stored
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

    private fun getMatchesOfDate(
        date: String
    ): String {
        return "fixtures/date/$date"
    }

    private fun getLiveMatches(): String {
        return "fixtures/live"
    }
}
