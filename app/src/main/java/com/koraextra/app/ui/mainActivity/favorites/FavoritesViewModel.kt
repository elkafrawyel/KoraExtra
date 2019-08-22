package com.koraextra.app.ui.mainActivity.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.FavoriteModel
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.Injector.getMatchesRepo
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel : KoraViewModel() {

    var opened: Boolean = false

    //==============================================================
    private var job: Job? = null

    private fun getFavoriteRepo() = Injector.favoriteRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    private var _uiStateFavo = MutableLiveData<MyUiStates>()
    val uiStateFavo: LiveData<MyUiStates>
        get() = _uiStateFavo

    var favoritesList: List<FavoriteModel>? = null

    fun getFavoritesList() {
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
            val result= getFavoriteRepo().getFavorite(Injector.getPreferenceHelper().token!!)

            when (result) {
                is DataResource.Success -> {
                    favoritesList = result.data.data
                    if (favoritesList!!.isNotEmpty()) {
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

    fun removeTeamToFavorite(item:FavoriteModel,token: String) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJobRemove(item,token)
        } else {
            _uiStateFavo.value = MyUiStates.NoConnection
        }
    }

    private fun launchJobRemove(item:FavoriteModel,token :String): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiStateFavo.value = MyUiStates.Loading }
            val result = getFavoriteRepo().removeToFavorite(item.teamId!!.toInt(), token)

            when (result) {
                is DataResource.Success -> {
                    if (result.data.status) {
                        getFavoriteRepo().updateTeam(item.teamId.toInt(), item.teamName!!, item.teamLogo!!, 0)
                        withContext(dispatcherProvider.main) {
                            _uiStateFavo.value = MyUiStates.Success
                        }

                    } else {
                        //NO Matches Stored
                        withContext(dispatcherProvider.main) {
                            _uiStateFavo.value = MyUiStates.Error(result.data.ar)
                        }
                    }

                }
                is DataResource.Error -> {

                    withContext(dispatcherProvider.main) {
                        _uiStateFavo.value = MyUiStates.Error(result.exception.message!!)
                    }
                }
            }
        }
    }
}
