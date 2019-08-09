package com.koraextra.app.ui.mainActivity.team.teamPlayers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import kotlinx.android.synthetic.main.team_players_fragment.*

class TeamPlayersFragment : Fragment() {

    companion object {
        fun newInstance() = TeamPlayersFragment()
    }

    private lateinit var viewModel: TeamPlayersViewModel
    private val adapterPlayers = AdapterPlayers()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_players_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamPlayersViewModel::class.java)
//        val players = ArrayList<String>()
//        players.add("a")
//        players.add("a")
//        players.add("a")
//        players.add("a")
//        players.add("a")
//
//        adapterPlayers.replaceData(players)
//
//        teamPlayerRv.adapter = adapterPlayers
//        teamPlayerRv.setHasFixedSize(true)
    }

}
