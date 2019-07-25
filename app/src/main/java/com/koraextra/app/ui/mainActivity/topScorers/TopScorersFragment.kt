package com.koraextra.app.ui.mainActivity.topScorers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class TopScorersFragment : Fragment() {

    companion object {
        fun newInstance() = TopScorersFragment()
    }

    private lateinit var viewModel: TopScorersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_scorers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopScorersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
