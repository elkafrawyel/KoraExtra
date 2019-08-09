package com.koraextra.app.ui.mainActivity.tournament.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentMatchesViewModel : KoraViewModel() {

    var opened:Boolean = false

    var tournament: LeagueModel? = null
    private var job: Job? = null
    private fun getLeagueMatchesRepo() = Injector.getLeaguesMatchesRepo()
    private fun getStoredMatches() = Injector.getStoredMatchesRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var storedMatchesLiveData: LiveData<List<MatchModel>>? = null

    fun getMatchesList() {
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

            when (val result = getLeagueMatchesRepo().getLeagueMatches(getQuery(tournament?.leagueId!!.toString()))) {
                is DataResource.Success -> {
                    if (result.data) {

                        when (val databaseResult = getStoredMatches().getStoredLeagueMatches(tournament?.leagueId!!)) {
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


    private fun getQuery(leagueId: String): String {
        return "fixtures/league/$leagueId"
    }
}
