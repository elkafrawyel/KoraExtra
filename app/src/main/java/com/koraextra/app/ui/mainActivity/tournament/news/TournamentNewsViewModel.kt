package com.koraextra.app.ui.mainActivity.tournament.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TournamentNewsViewModel : KoraViewModel() {

    private var job: Job? = null
    var leagueModel: LeagueModel? = null
    private fun getNewsRepo() = Injector.getNewsRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var newsList: ArrayList<KoraNewsModel> = arrayListOf()

    fun getNews() {
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
//            leagueModel?.leagueId.toString()
            when (val result = getNewsRepo().getLeagueNews("10")) {
                is DataResource.Success -> {
                    newsList.addAll(result.data)
                    withContext(dispatcherProvider.main) {
                        _uiState.value = MyUiStates.Success
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
