package com.koraextra.app.ui.mainActivity.match.matchTops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.team.teamPlayers.AdapterPlayers
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
import kotlinx.android.synthetic.main.match_tops_fragment.*

class MatchTopsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchTopsFragment()
    }

    private lateinit var viewModel: MatchTopsViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_tops_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchTopsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        mainViewModel.matchLiveData.observe(this, Observer {
            //            activity?.toast("Match :${it.fixtureId}")
            viewModel.fixtureId = it?.fixtureId
            viewModel.getMatchTopsList()
        })

        viewModel.uiState.observe(this, Observer {
            onTopsResponse(it)
        })

//        val players = ArrayList<String>()
//        players.add("a")
//        players.add("a")
//        players.add("a")
//        players.add("a")
//        players.add("a")
//
//        adapterPlayers.replaceData(players)
//
//        matchPlayerRv.adapter = adapterPlayers
//        matchPlayerRv.setHasFixedSize(true)

    }

    private fun onTopsResponse(state: MyUiStates?) {

        when (state) {
            MyUiStates.Loading -> {

                loading.visibility = View.VISIBLE
                emptyMessageTv.visibility = View.GONE
                matchPlayerRv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                matchPlayerRv.visibility = View.VISIBLE

                onMatchTopsSuccess()
            }
            MyUiStates.LastPage -> {
                emptyMessageTv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                activity?.snackBar(state.message, matchTopsRootView)
                emptyMessageTv.visibility = View.GONE
                matchPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {
                emptyMessageTv.visibility = View.GONE
                matchPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
                activity?.snackBarWithAction(
                    context!!.resources.getString(R.string.noConnectionMessage),
                    matchTopsRootView
                ) {
                    viewModel.fixtureId?.let {
                        viewModel.getMatchTopsList()
                    }
                }
            }
            MyUiStates.Empty -> {
                emptyMessageTv.visibility = View.VISIBLE
                matchPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            null -> {

            }
        }
    }

    private fun onMatchTopsSuccess() {
        val tops = viewModel.matchTops
        val adapterPlayers = AdapterPlayers()
        adapterPlayers.replaceData(tops!!)
        matchPlayerRv.adapter = adapterPlayers
        matchPlayerRv.setHasFixedSize(true)
    }

}
