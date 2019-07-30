package com.koraextra.app.ui.mainActivity.players

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.players_fragment.*

class PlayersFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersFragment()
    }

    private lateinit var viewModel: PlayersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersViewModel::class.java)

        playerNameTv.text = "محمد صلاح"

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        PlayerViewPagerAdapter(fragmentManager!!).also {
            playerTabs.setupWithViewPager(view_pager)
            view_pager.adapter = it
        }
    }

}
