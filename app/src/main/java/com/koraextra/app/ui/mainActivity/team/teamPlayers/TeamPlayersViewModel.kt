package com.koraextra.app.ui.mainActivity.team.teamPlayers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.Player
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamPlayersViewModel : KoraViewModel() {
    var teamId: Int? = null

    //==============================================================
    private var job: Job? = null

    private fun getMatchTopsRepo() = Injector.getMatchTopsRepo()


    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var matchTops: List<Player>? = null

    fun getTeamPlayersTopsList() {
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
            when (val result = getMatchTopsRepo().getMatchTops(getPlayersOfTeam(teamId!!))) {
                is DataResource.Success -> {
                    if (result.data.isNotEmpty()) {
                        matchTops = result.data
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
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

    private fun getPlayersOfTeam(
        teamId: Int
    ): String {
        return "players/team/$teamId"
    }

}