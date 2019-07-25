package com.koraextra.app.ui.mainActivity.match.matchDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class MatchDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchDetailsFragment()
    }

    private lateinit var viewModel: MatchDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
