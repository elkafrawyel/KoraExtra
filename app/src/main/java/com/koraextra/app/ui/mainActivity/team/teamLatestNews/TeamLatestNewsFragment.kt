package com.koraextra.app.ui.mainActivity.team.teamLatestNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class TeamLatestNewsFragment : Fragment() {

    companion object {
        fun newInstance() = TeamLatestNewsFragment()
    }

    private lateinit var viewModel: TeamLatestNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_latest_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamLatestNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
