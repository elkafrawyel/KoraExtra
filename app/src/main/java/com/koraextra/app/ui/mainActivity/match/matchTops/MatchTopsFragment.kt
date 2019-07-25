package com.koraextra.app.ui.mainActivity.match.matchTops

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class MatchTopsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchTopsFragment()
    }

    private lateinit var viewModel: MatchTopsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_tops_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchTopsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
