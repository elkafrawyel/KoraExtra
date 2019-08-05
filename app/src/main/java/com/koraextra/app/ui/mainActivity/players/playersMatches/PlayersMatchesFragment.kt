package com.koraextra.app.ui.mainActivity.players.playersMatches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.testMatchModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import kotlinx.android.synthetic.main.players_matches_fragment.*

class PlayersMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersMatchesFragment()
    }

    private lateinit var viewModel: PlayersMatchesViewModel
    private val list: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_matches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersMatchesViewModel::class.java)

        setUpMatches()
    }

    private fun setUpMatches() {
        list.clear()

        adapterMatches.notifyDataSetChanged()

        teamMatchesRv.adapter = adapterMatches
        teamMatchesRv.setHasFixedSize(true)
    }
}
