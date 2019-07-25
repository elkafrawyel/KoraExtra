package com.koraextra.app.ui.mainActivity.match.matchEvents

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class MatchEventsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchEventsFragment()
    }

    private lateinit var viewModel: MatchEventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
