package com.koraextra.app.ui.mainActivity.team.teamPlayers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter

import com.koraextra.app.R
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
import kotlinx.android.synthetic.main.team_players_fragment.*

class TeamPlayersFragment : Fragment(), BaseQuickAdapter.OnItemChildClickListener {


    companion object {
        fun newInstance() = TeamPlayersFragment()
    }

    private lateinit var viewModel: TeamPlayersViewModel
    private lateinit var mainViewModel: MainViewModel
    private val adapterPlayers = AdapterPlayers().also {
        it.onItemChildClickListener = this
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_players_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamPlayersViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

       if (viewModel.matchTops.isEmpty()){
           mainViewModel.leagueIdLiveData.observe(this, Observer {
               viewModel.leagueId = it
           })
           mainViewModel.teamIdLiveData.observe(this, Observer {
               viewModel.teamId = it
               viewModel.getTeamPlayersTopsList()
           })

           viewModel.uiState.observe(this, Observer {
               onTopsResponse(it)
           })
       }


        teamPlayerRv.adapter = adapterPlayers
        teamPlayerRv.setHasFixedSize(true)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        mainViewModel.setPlayer((adapter?.data!![position] as PlayerModel?)!!)
        when (view?.id) {
            R.id.player_item -> {
                activity?.findNavController(R.id.fragment)?.navigate(R.id.playersFragment)
            }
        }
    }

    private fun onTopsResponse(state: MyUiStates?) {

        when (state) {
            MyUiStates.Loading -> {

                loading.visibility = View.VISIBLE
                emptyMessageTv.visibility = View.GONE
                teamPlayerRv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                teamPlayerRv.visibility = View.VISIBLE

                onMatchTopsSuccess()
            }
            MyUiStates.LastPage -> {
                emptyMessageTv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                activity?.snackBar(state.message, teamPlayerRootView)
                emptyMessageTv.visibility = View.GONE
                teamPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {
                emptyMessageTv.visibility = View.GONE
                teamPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
                activity?.snackBarWithAction(
                    context!!.resources.getString(R.string.noConnectionMessage),
                    teamPlayerRootView
                ) {
                    viewModel.teamId?.let {
                        viewModel.getTeamPlayersTopsList()
                    }
                }
            }
            MyUiStates.Empty -> {
                emptyMessageTv.visibility = View.VISIBLE
                teamPlayerRv.visibility = View.GONE

                loading.visibility = View.GONE
            }
            null -> {

            }
        }
    }

    private fun onMatchTopsSuccess() {
        loading.visibility = View.GONE
        emptyMessageTv.visibility = View.GONE
        teamPlayerRv.visibility = View.VISIBLE
        adapterPlayers.replaceData(viewModel.matchTops)
    }

}
