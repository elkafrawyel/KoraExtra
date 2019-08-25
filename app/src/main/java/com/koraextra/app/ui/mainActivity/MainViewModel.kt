package com.koraextra.app.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.data.models.auth.SocialBody
import com.koraextra.app.ui.KoraViewModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Event
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : KoraViewModel() {

    private var _match = MutableLiveData<MatchModel>()
    val matchLiveData: LiveData<MatchModel>
        get() = _match

    var useFirstId = true
    fun setMatch(match: MatchModel) {
        _match.value = match
    }

    private var _tournament = MutableLiveData<LeagueModel>()
    val tournamentLiveData: LiveData<LeagueModel>
        get() = _tournament

    fun setTournament(tournament: LeagueModel) {
        _tournament.value = tournament
    }

    private var _teamId = MutableLiveData<Int>()
    val teamIdLiveData: LiveData<Int>
        get() = _teamId

    fun setTeamId(teamId: Int) {
        _teamId.value = teamId
    }

    private var _teamName = MutableLiveData<String>()
    val teamNameLiveData: LiveData<String>
        get() = _teamName

    fun setTeamName(teamName: String) {
        _teamName.value = teamName
    }

    private var _teamLogo = MutableLiveData<String>()
    val teamLogoLiveData: LiveData<String>
        get() = _teamLogo

    fun setTeamLogo(teamLogo: String) {
        _teamLogo.value = teamLogo
    }

    private var _teamFavo = MutableLiveData<Int>()
    val teamFavoLiveData: LiveData<Int>
        get() = _teamFavo

    fun setTeamFavo(teamFavo: Int) {
        _teamFavo.value = teamFavo
    }

    private var _leagueId = MutableLiveData<Int>()
    val leagueIdLiveData: LiveData<Int>
        get() = _leagueId

    fun setLeagueId(leagueId: Int) {
        _leagueId.value = leagueId
    }

    var player = MutableLiveData<PlayerModel>()
    val playerLiveData: LiveData<PlayerModel>
        get() = player

    fun setPlayer(player: PlayerModel) {
        this._teamId.value = player.teamId!!
        this._teamName.value = player.teamName!!
        this._teamLogo.value = player.teamimage!!
        this.player.value = player
    }

    var playerId = MutableLiveData<Int>()
    val playerIdLiveData: LiveData<Int>
        get() = playerId

    fun setplayerId(playerId: Int) {
        this.playerId.value = playerId
    }


    //================================== FaceBook Login ======================

    private var job: Job? = null

    private fun socialRepo() = Injector.getSocialRepo()
    private fun preferencesHelper() = Injector.getPreferenceHelper()

    private var _uiState = MutableLiveData<Event<MyUiStates>>()
    val uiState: LiveData<Event<MyUiStates>>
        get() = _uiState

    fun socialLogin(body: SocialBody) {
        if (NetworkUtils.isConnected()) {
            if (job?.isActive == true)
                return
            job = launchJob(body)
        } else {
            _uiState.value = Event(MyUiStates.NoConnection)
        }
    }

    private fun launchJob(body: SocialBody): Job {
        return scope.launch(dispatcherProvider.io) {
            withContext(dispatcherProvider.main) { _uiState.value = Event(MyUiStates.Loading) }

            when (val response = socialRepo().login(body)) {
                is DataResource.Success -> {
                    withContext(dispatcherProvider.main) {
                        if (response.data.status!!) {
//                            preferencesHelper().id = response.data.id!!
                            preferencesHelper().name = body.name
                            preferencesHelper().email = body.email
                            preferencesHelper().token = body.api_token
                            preferencesHelper().isLoggedIn = true
                            _uiState.value = Event(MyUiStates.Success)
                        } else {
                            _uiState.value = Event(MyUiStates.Error(response.data.ar!!))
                        }
                    }
                }
                is DataResource.Error -> {
                    withContext(dispatcherProvider.main) {
                        _uiState.value = Event(MyUiStates.Error(response.exception.message!!))
                    }
                }
            }
        }
    }
}