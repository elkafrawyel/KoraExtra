package com.koraextra.app.ui.mainActivity.tournament

import com.koraextra.app.ui.KoraViewModel

class TournamentViewModel : KoraViewModel() {
    var tournamentId: Int? = null

    var opened: Boolean = false
    var selectedTab: Int = 0
    var selectedFromTabs: Boolean = true
}
