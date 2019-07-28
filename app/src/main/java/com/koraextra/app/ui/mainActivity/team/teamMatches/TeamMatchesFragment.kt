package com.koraextra.app.ui.mainActivity.team.teamMatches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import kotlinx.android.synthetic.main.team_matches_fragment.*

class TeamMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = TeamMatchesFragment()
    }

    private lateinit var viewModel: TeamMatchesViewModel
    private val list: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(list)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_matches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamMatchesViewModel::class.java)

        setUpMatches()
    }

    private fun setUpMatches() {

        list.add(MatchModel(0))
        list.add(MatchModel(1))
        list.add(MatchModel(0))
        list.add(MatchModel(2))

        adapterMatches.notifyDataSetChanged()

        teamMatchesRv.adapter = adapterMatches
        teamMatchesRv.setHasFixedSize(true)
    }
}
