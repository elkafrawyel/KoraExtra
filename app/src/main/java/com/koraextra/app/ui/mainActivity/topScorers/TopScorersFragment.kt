package com.koraextra.app.ui.mainActivity.topScorers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.top_scorers_fragment.*

class TopScorersFragment : Fragment() {

    companion object {
        fun newInstance() = TopScorersFragment()
    }

    private lateinit var viewModel: TopScorersViewModel
    private val adapterTopScorer = AdapterTopScorer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_scorers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopScorersViewModel::class.java)
        backImage.setOnClickListener {
            findNavController().navigateUp()
        }
        val list = ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")

        adapterTopScorer.replaceData(list)
        topScorerRv.adapter = adapterTopScorer
        topScorerRv.setHasFixedSize(true)
    }

}
