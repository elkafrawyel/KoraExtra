package com.koraextra.app.ui.mainActivity.latestNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class LatestNewsFragment : Fragment() {

    companion object {
        fun newInstance() = LatestNewsFragment()
    }

    private lateinit var viewModel: LatestNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.latest_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LatestNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
