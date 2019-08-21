package com.koraextra.app.ui.mainActivity.tournament.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.GroupTable
import com.koraextra.app.data.models.LeagueTableModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentOrderViewModel : KoraViewModel() {
    var tournament: LeagueModel? = null


    private var job: Job? = null

    private fun getLeagueTableRepo() = Injector.getLeagueTableRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var tableList: ArrayList<LeagueTableModel> = arrayListOf()

    fun getLeagueTable() {
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

            when (val result = getLeagueTableRepo().getLeagueTable(getQuery(tournament?.leagueId!!.toString()))) {
                is DataResource.Success -> {
                    if (result.data.api?.results!! > 0) {
                        tableList.clear()
                        //convertData
                        result.data.api.groupTable.forEach {
                            tableList.add(LeagueTableModel(true, it.groupname, true))
                            it.group.forEach { groupTeam ->
                                tableList.add(LeagueTableModel(groupTeam))
                            }
                        }
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
                        }
                    } else {
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

    private fun getQuery(
        leagueId: String
    ): String {
        return "leagueTable/$leagueId"
    }
}
