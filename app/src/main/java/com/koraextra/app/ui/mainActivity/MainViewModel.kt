package com.koraextra.app.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.MatchModel
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

}