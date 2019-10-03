package com.koraextra.app.ui.mainActivity.match.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.MatchVideoModel
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchVideosViewModel : KoraViewModel() {
    private var job: Job? = null

    var match: MatchModel? = null

    private var _uiState = MutableLiveData<MyUiStates>()
    val uiState: LiveData<MyUiStates>
        get() = _uiState

    var videos: ArrayList<MatchVideoModel> = arrayListOf()

    fun getVideos() {
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
            when (val result =
                Injector.getNewsRepo().getMatchVideos(match!!.fixtureId!!.toString())) {
                is DataResource.Success -> {
                    videos.clear()
                    videos.addAll(result.data.videos)
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
