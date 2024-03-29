package com.koraextra.app.ui.mainActivity.tournament.order

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter

import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueTableModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
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
            if (viewModel.tableList.size == 0)
                viewModel.getLeagueTable()
        })


    }

    private fun onLeagueTableResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                emptyMessageTv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                onTableSuccess()
            }
            MyUiStates.LastPage -> {

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                activity?.snackBar(states.message, rootView)
            }
            MyUiStates.NoConnection -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                activity?.snackBarWithAction(
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    rootView
                ) {
                    viewModel.getLeagueTable()
                }
            }
            MyUiStates.Empty -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.VISIBLE
            }
            null -> {
            }
        }
    }

    private fun onTableSuccess() {

        leagueTableAdapter =
            AdapterLeagueTable(
                R.layout.league_table_item_view,
                R.layout.table_header_item_view,
                viewModel.tableList
            ).also {
                it.onItemChildClickListener =
                    BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                        val groupTeam = ((adapter.data[position]) as LeagueTableModel).t
                        when (view?.id) {
                            R.id.teamRow -> {
                                mainViewModel.setTeamId(groupTeam.teamId!!)
                                mainViewModel.setTeamName(groupTeam.teamName!!)
                                mainViewModel.setTeamLogo(groupTeam.logo!!)
                                mainViewModel.setTeamFavo(groupTeam.favorite)
                                mainViewModel.setLeagueId(viewModel.tournament?.leagueId!!)

                                findNavController().navigate(R.id.teamFragment)
                            }
                        }
                    }
            }

        tableRv.setHasFixedSize(true)
        tableRv.adapter = leagueTableAdapter
    }

}
