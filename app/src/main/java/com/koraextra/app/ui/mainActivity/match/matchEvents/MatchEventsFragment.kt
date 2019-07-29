package com.koraextra.app.ui.mainActivity.match.matchEvents

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchEventsModel
import kotlinx.android.synthetic.main.match_events_fragment.*

class MatchEventsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchEventsFragment()
    }

    private lateinit var viewModel: MatchEventsViewModel
    private val list: ArrayList<MatchEventsModel> = arrayListOf()
    private val adapterMatchEvents= AdapterMatchEvents(list)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchEventsViewModel::class.java)

        setUpMatchEvents()
    }

    private fun setUpMatchEvents() {
        list.clear()
        list.add(MatchEventsModel(0))
        list.add(MatchEventsModel(1))
        list.add(MatchEventsModel(2))
        list.add(MatchEventsModel(1))
        list.add(MatchEventsModel(2))
        list.add(MatchEventsModel(3))
        list.add(MatchEventsModel(1))
        list.add(MatchEventsModel(1))
        list.add(MatchEventsModel(3))

        adapterMatchEvents.notifyDataSetChanged()
        matchEventsRv.adapter = adapterMatchEvents
        matchEventsRv.setHasFixedSize(true)

    }

}
