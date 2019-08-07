package com.koraextra.app.ui.mainActivity.match.matchEvents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchEventsViewModel : KoraViewModel() {
    var homeTeamId: Int? = null
    var awayTeamId: Int? = null
    var fixtureId: Int? = null

    //==============================================================
    private var job: Job? = null

    private fun getMatchEventsRepo() = Injector.getMatchEventsRepo()
    private fun getStoredMatchEvents() = Injector.getStoredMatchEventsRepo()


    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var matchEventsLiveData: LiveData<List<EventModel>>? = null

    fun getMatchEventsList() {
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
//            141565
            when (val result = getMatchEventsRepo().getMatchEvents(getEventsOfMatch(fixtureId!!))) {
                is DataResource.Success -> {
                    if (result.data) {

                        when (val databaseResult = getStoredMatchEvents().getStoredMatchEvents(fixtureId!!)) {
                            is DataResource.Success -> {
                                matchEventsLiveData = databaseResult.data
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

    private fun getEventsOfMatch(
        fixtureId: Int
    ): String {
        return "/events/$fixtureId"
    }

}