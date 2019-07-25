package com.koraextra.app.ui.mainActivity.players.playersMatches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class PlayersMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersMatchesFragment()
    }

    private lateinit var viewModel: PlayersMatchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_matches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersMatchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
