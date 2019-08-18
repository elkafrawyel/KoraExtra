package com.koraextra.app.ui.mainActivity.match.matchEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.koraextra.app.R
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
import kotlinx.android.synthetic.main.match_events_fragment.*

class MatchEventsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchEventsFragment()
    }

    private lateinit var viewModel: MatchEventsViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchEventsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)



        mainViewModel.matchLiveData.observe(this, Observer {
            //            activity?.toast("Match :${it.fixtureId}")
            viewModel.homeTeamId = it?.homeTeam?.teamId
            viewModel.awayTeamId = it?.awayTeam?.teamId
            viewModel.fixtureId = it?.fixtureId
            viewModel.getMatchEventsList()
        })

        viewModel.uiState.observe(this, Observer {
            onEventsResponse(it)
        })
    }

    private fun onEventsResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {

                loading.visibility = View.VISIBLE
                emptyMessageTv.visibility = View.GONE
                matchEventsRv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                matchEventsRv.visibility = View.VISIBLE

                onEventsSuccess()
            }
            MyUiStates.LastPage -> {
                emptyMessageTv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                activity?.snackBar(states.message, matchEventRootView)
                emptyMessageTv.visibility = View.GONE
                matchEventsRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {
                emptyMessageTv.visibility = View.GONE
                matchEventsRv.visibility = View.GONE

                loading.visibility = View.GONE
                activity?.snackBarWithAction(
                    context!!.resources.getString(R.string.noConnectionMessage),
                    matchEventRootView
                ) {
                    viewModel.fixtureId?.let {
                        viewModel.getMatchEventsList()
                    }
                }
            }
            MyUiStates.Empty -> {
                emptyMessageTv.visibility = View.VISIBLE
                matchEventsRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            null -> {

            }
        }
    }

    private fun onEventsSuccess() {
        viewModel.matchEventsLiveData?.observe(this, Observer { it ->
            val events = arrayListOf<EventModel>()
            it.forEach {
                if (it.teamId == viewModel.homeTeamId) {
                    it.viewType = 1
                } else {
                    it.viewType = 2
                }
            }
            events.addAll(it)
            if (events.size > 0)
                events.add(events.size, EventModel("", 0, "", "", 0, 0, "", "", 0, viewModel.fixtureId, 0))

            val adapterMatchEvents = AdapterMatchEvents(events).also {
                it.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                    mainViewModel.setplayerId((adapter.data[position] as EventModel?)!!.playerId!!)
                    if (view.id == R.id.PlayerName)
                        activity?.findNavController(R.id.fragment)?.navigate(R.id.playersFragment)
                }
            }
            matchEventsRv.adapter = adapterMatchEvents
            matchEventsRv.setHasFixedSize(true)
        })
    }
}
