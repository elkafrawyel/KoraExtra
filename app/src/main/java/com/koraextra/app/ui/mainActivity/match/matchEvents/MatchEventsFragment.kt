package com.koraextra.app.ui.mainActivity.match.matchEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseQuickAdapter
import com.koraextra.app.R
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
import kotlinx.android.synthetic.main.match_events_fragment.*
import android.content.Intent
import android.net.Uri

class MatchEventsFragment : Fragment(), Observer<MatchModel> {

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

        mainViewModel.matchLiveData.observe(this, this)

        viewModel.uiState.observe(this, Observer {
            onEventsResponse(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.useFirstId = false
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
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
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

    override fun onChanged(it: MatchModel) {
        if (mainViewModel.useFirstId) {
            viewModel.homeTeamId = it.homeTeam?.teamId
            viewModel.awayTeamId = it.awayTeam?.teamId
            viewModel.fixtureId = it.fixtureId
            viewModel.getMatchEventsList()
        } else {
            mainViewModel.useFirstId = true
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
                events.add(
                    events.size,
                    EventModel(
                        "",
                        0,
                        "",
                        "",
                        0,
                        0,
                        "",
                        "",
                        "",
                        0,
                        viewModel.fixtureId,
                        0
                    )
                )

            val adapterMatchEvents = AdapterMatchEvents(events).also {
                it.onItemChildClickListener =
                    BaseQuickAdapter.OnItemChildClickListener { adapter
                                                                , view
                                                                , position ->
                        when(view.id){
                            R.id.eventItem ->{
                                val event = (adapter.data as List<EventModel>)[position]
                                if (event.youtube!=""){
                                    context!!.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(event.youtube)
                                        )
                                    )

                                }
                            }
                        }
                    }
            }
            matchEventsRv.adapter = adapterMatchEvents
            matchEventsRv.setHasFixedSize(true)
        })
    }
}
