package com.koraextra.app.ui.mainActivity.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.FavoriteBody
import com.koraextra.app.data.models.FavoriteResponse
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeamViewModel : KoraViewModel() {
    var id: Int? = null
    var name: String? = null
    var logo: String? = null
    var favorite: Int? = null
    var leagueId: Int? = null

    var opened: Boolean = false

    //==============================================================
    private var job: Job? = null

    private fun getFavoriteRepo() = Injector.favoriteRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState


    fun addTeamToFavorite(favoriteBody: FavoriteBody) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(favoriteBody)
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJob(favoriteBody: FavoriteBody): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            val result = getFavoriteRepo().addToFavorite(favoriteBody)

            when (result) {
                is DataResource.Success -> {
                    if (result.data.status) {
                        getFavoriteRepo().updateTeam(id!!, name!!, logo!!, favorite!!)
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
                        }

                    } else {
                        //NO Matches Stored
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Error(result.data.ar)
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

    fun removeTeamToFavorite(token: String) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJobRemove(token)
        } else {
            _uiState.value = MyUiStates.NoConnection
        }
    }

    private fun launchJobRemove(token: String): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = MyUiStates.Loading }
            val result = getFavoriteRepo().removeToFavorite(id!!, token)

            when (result) {
                is DataResource.Success -> {
                    if (result.data.status) {
                        getFavoriteRepo().updateTeam(id!!, name!!, logo!!, favorite!!)
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Success
                        }

                    } else {
                        //NO Matches Stored
                        withContext(dispatcherProvider.main) {
                            _uiState.value = MyUiStates.Error(result.data.ar)
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
