package com.koraextra.app.ui.mainActivity.tournaments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentsViewModel : KoraViewModel() {

    var opened: Boolean = false

    private var job: Job? = null
    private fun getLeaguesRepo() = Injector.getLeaguesRepo()
    private fun getSeasonsRepo() = Injector.getSeasonsRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var leagues: ArrayList<LeagueModel> = arrayListOf()
    var seasons: ArrayList<Int> = arrayListOf()

    var chooseThisYear: Boolean = true
    var loadSeasons: Boolean = false
    var currentSeasonPosition: Int = 0


    fun getSeasons() {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchSeasonsJob()
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchSeasonsJob(): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }

            when (val seasonsResult = getSeasonsRepo().getSeasons()) {
                is DataResource.Success -> {
                    seasons.clear()
                    seasons.addAll(seasonsResult.data.seasons)

                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Success
                    }

                    val season = seasons[seasons.size - 1].toString()
                    getLeaguesBySeason(season)

                }
                is DataResource.Error -> {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Error(seasonsResult.exception.message!!)
                    }
                }
            }

        }
    }

    fun getLeaguesBySeason(season: String) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchLeaguesJob(season)
        } else {
            _uiState.value = MyUiStates.NoConnection
        }

    }

    private fun launchLeaguesJob(season: String): Job? {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }

            when (val result = getLeaguesRepo().getLeagues(getQuery(season))) {
                is DataResource.Success -> {
                    if (result.data.isNotEmpty()) {
                        leagues.clear()
                        leagues.addAll(result.data)
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
}


private fun getQuery(
    year: String
): String {
    return "leagues/season/$year"
}
