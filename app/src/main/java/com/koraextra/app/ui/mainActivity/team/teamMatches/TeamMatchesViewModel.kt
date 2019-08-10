package com.koraextra.app.ui.mainActivity.team.teamMatches

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

class TeamMatchesViewModel : KoraViewModel() {


    var opened:Boolean = false

    var id:Int?=null
    var name:String?=null
    var logo:String?=null
    //==============================================================
    private var job: Job? = null
    private fun getMatchesRepo() = Injector.getMatchesRepo()
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
            var result: DataResource<Boolean>? = getMatchesRepo().getMatches(getMatchesOfTeam(id!!))


            when (result) {
                is DataResource.Success -> {
                    if (result.data) {

                        when (val databaseResult = getStoredMatches().getStoredTeamMatches(id!!,name!!,logo!!)) {
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

    private fun getMatchesOfTeam(
        teamId: Int
    ): String {
        return "fixtures/team/$teamId"
    }
}
