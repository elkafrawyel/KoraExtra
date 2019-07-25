package com.koraextra.app.ui.mainActivity.players.playersDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class PlayersDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersDetailsFragment()
    }

    private lateinit var viewModel: PlayersDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
