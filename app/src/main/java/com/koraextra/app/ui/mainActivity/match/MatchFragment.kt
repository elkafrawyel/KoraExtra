package com.koraextra.app.ui.mainActivity.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.koraextra.app.R
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.match_fragment.*

class MatchFragment : Fragment() {

    companion object {
        fun newInstance() = MatchFragment()
    }

    private lateinit var viewModel: MatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        // TODO: Use the ViewModel

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }


        teamShareImage.setOnClickListener {
            activity?.toast("Added to your Share")
        }

        MatchViewPagerAdapter(fragmentManager!!).also {
            matchTabs.setupWithViewPager(view_pager)
            view_pager.adapter = it
        }
    }

}
