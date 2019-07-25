package com.koraextra.app.ui.mainActivity.tournaments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R

class TournamentsFragment : Fragment() {

    companion object {
        fun newInstance() = TournamentsFragment()
    }

    private lateinit var viewModel: TournamentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournaments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
