package com.koraextra.app.ui.mainActivity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.cobonee.app.utily.DataResource
import com.cobonee.app.utily.MyUiStates
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.Injector
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : KoraViewModel() {

    private var job: Job? = null
    private fun getMatchesRepo() = Injector.getMatchesRepo()
    private fun getStoredMatches() = Injector.getStoredMatchesRepo()

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var storedMatchesLiveData: LiveData<List<MatchModel>>? = null

    var matchesList: ArrayList<MatchModel> = arrayListOf()

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
            val result = getMatchesRepo().getMatches(makeParameter())

            when (result) {
                is DataResource.Success -> {

                    if (result.data) {
                        val databaseResult = getStoredMatches().getStoredMatches("")

                        when (databaseResult) {
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

                    //    matchesList.clear()
//                        result.data.response?.matchModels?.forEach {
//                            matchesList.add(it!!)
//                        }

//                        matchesList.addAll(result.data.response?.matchModels as List<MatchModel>)

                }
                is DataResource.Error -> {

                    withContext(dispatcherProvider.main) {
                    _uiState.value = MyUiStates.Error(result.exception.message!!)
                    }
                }
            }

            withContext(dispatcherProvider.main) {
                when (result) {
                    is DataResource.Success -> {


//                        matchesList.clear()
//                        result.data.response?.matchModels?.forEach {
//                            matchesList.add(it!!)
//                        }

//                        matchesList.addAll(result.data.response?.matchModels as List<MatchModel>)

                        _uiState.value = MyUiStates.Success
                    }
                    is DataResource.Error -> {
                        _uiState.value = MyUiStates.Error(result.exception.message!!)
                    }
                }
            }
        }
    }

    private fun makeParameter(): String {
        return "fixtures/live"
    }

}
