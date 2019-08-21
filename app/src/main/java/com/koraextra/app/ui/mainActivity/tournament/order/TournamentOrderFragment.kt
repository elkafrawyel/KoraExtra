package com.koraextra.app.ui.mainActivity.tournament.order

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import kotlinx.android.synthetic.main.tournament_order_fragment.*

class TournamentOrderFragment : Fragment() {

    companion object {
        fun newInstance() = TournamentOrderFragment()
    }

    private lateinit var viewModel: TournamentOrderViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var leagueTableAdapter: AdapterLeagueTable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournament_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentOrderViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        viewModel.uiState.observe(this, Observer { onLeagueTableResponse(it) })

        mainViewModel.tournamentLiveData.observe(this, Observer {
            viewModel.tournament = it
            viewModel.getLeagueTable()
        })


    }

    private fun onLeagueTableResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                onTableSuccess()
            }
            MyUiStates.LastPage -> {

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE

            }
            MyUiStates.NoConnection -> {
                loading.visibility = View.GONE

            }
            MyUiStates.Empty -> {
                loading.visibility = View.GONE

            }
            null -> {
            }
        }
    }

    private fun onTableSuccess() {

        leagueTableAdapter =
            AdapterLeagueTable(R.layout.league_table_item_view, R.layout.table_header_item_view, viewModel.tableList)

        tableRv.setHasFixedSize(true)
        tableRv.adapter = leagueTableAdapter
    }

}
