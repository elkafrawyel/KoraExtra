package com.koraextra.app.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.ui.KoraViewModel

class MainViewModel : KoraViewModel() {

    private var _match = MutableLiveData<MatchModel>()
    val matchLiveData: LiveData<MatchModel>
        get() = _match

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

}