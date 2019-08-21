package com.koraextra.app.ui.mainActivity.team.teamGroup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import kotlinx.android.synthetic.main.team_group_fragment.*

class TeamGroupFragment : Fragment() {

    companion object {
        fun newInstance() = TeamGroupFragment()
    }

    private lateinit var viewModel: TeamGroupViewModel
//    private val adapterTeamGroup = AdapterTeamGroup()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_group_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamGroupViewModel::class.java)
//        val group = ArrayList<String>()
//        group.add("a")
//        group.add("a")
//        group.add("a")
//        group.add("a")
//
//
//        adapterTeamGroup.replaceData(group)
//
//        teamGroupRv.adapter = adapterTeamGroup
//        teamGroupRv.setHasFixedSize(true)
    }

}
