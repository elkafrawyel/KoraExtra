package com.koraextra.app.ui.mainActivity.match.matchNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class MatchNewsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchNewsFragment()
    }

    private lateinit var viewModel: MatchNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
