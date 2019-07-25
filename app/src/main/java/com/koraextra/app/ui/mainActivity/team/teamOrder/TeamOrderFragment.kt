package com.koraextra.app.ui.mainActivity.team.teamOrder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class TeamOrderFragment : Fragment() {

    companion object {
        fun newInstance() = TeamOrderFragment()
    }

    private lateinit var viewModel: TeamOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamOrderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
