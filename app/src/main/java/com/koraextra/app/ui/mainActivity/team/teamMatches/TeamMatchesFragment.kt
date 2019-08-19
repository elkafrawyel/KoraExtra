package com.koraextra.app.ui.mainActivity.team.teamMatches

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
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.team_matches_fragment.*

class TeamMatchesFragment : Fragment(), Observer<List<MatchModel>>, BaseQuickAdapter.OnItemChildClickListener {


    companion object {
        fun newInstance() = TeamMatchesFragment()
    }

    private lateinit var viewModel: TeamMatchesViewModel
    private lateinit var mainViewModel: MainViewModel

    private val matchesList: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(matchesList).also {
        it.onItemChildClickListener = this
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_matches_fragment, container, false)
    }

    override fun onChanged(it: List<MatchModel>?) {
        setUpMatches(it!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamMatchesViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        if (viewModel.storedMatchesLiveData == null) {

            mainViewModel.teamIdLiveData.observe(this, Observer {
                viewModel.id = it
            })

            mainViewModel.teamNameLiveData.observe(this, Observer {
                viewModel.name = it

            })

            mainViewModel.teamLogoLiveData.observe(this, Observer {
                viewModel.logo = it
            })

            viewModel.uiState.observe(this, Observer<MyUiStates?> { onMatchesChanged(it) })

            viewModel.getMatchesList()
        }

        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)
    }


    private fun onMatchesChanged(state: MyUiStates?) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            MyUiStates.Success -> {
                viewModel.storedMatchesLiveData?.observe(this, this)
            }

            MyUiStates.LastPage -> {
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                activity?.snackBar(state.message, root)
            }

            MyUiStates.NoConnection -> {
                //show no connect view
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                activity?.snackBarWithAction(
                    context?.resources?.getString(R.string.noConnectionMessage),
                    root
                ) {
                    viewModel.getMatchesList()
                }
            }
            MyUiStates.Empty -> {
                //show Empty view
                emptyMessageTv.visibility = View.VISIBLE
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE

            }
            null -> {
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val match = (adapter?.data as List<MatchModel>)[position]
        when (view?.id) {
            R.id.matchItem -> {
                val bundle = Bundle()
                bundle.putInt("fixtureId", match.fixtureId!!)
                activity?.findNavController(R.id.fragment)?.navigate(R.id.matchFragment, bundle)
                viewModel.storedMatchesLiveData?.removeObserver(this)

            }

            R.id.homeImg,
            R.id.homeName -> {
                viewModel.id = match.homeTeam?.teamId!!
                viewModel.name = match.homeTeam.teamName!!
                viewModel.logo = match.homeTeam.logo!!

                mainViewModel.setTeamId(match.homeTeam.teamId)
                mainViewModel.setTeamName(match.homeTeam.teamName)
                mainViewModel.setTeamLogo(match.homeTeam.logo)
                viewModel.getMatchesList()
                viewModel.storedMatchesLiveData?.removeObserver(this)

            }

            R.id.awayImg,
            R.id.awayName -> {
                viewModel.id = match.homeTeam?.teamId!!
                viewModel.name = match.homeTeam.teamName!!
                viewModel.logo = match.homeTeam.logo!!

                mainViewModel.setTeamId(match.awayTeam?.teamId!!)
                mainViewModel.setTeamName(match.awayTeam.teamName!!)
                mainViewModel.setTeamLogo(match.awayTeam.logo!!)
                viewModel.getMatchesList()
                viewModel.storedMatchesLiveData?.removeObserver(this)

            }
        }
    }

    private fun setUpMatches(list: List<MatchModel>) {

        matchesList.clear()
        matchesList.addAll(list)
        adapterMatches.notifyDataSetChanged()
        loading.visibility = View.GONE
        emptyMessageTv.visibility = View.GONE
        matchesRv.visibility = View.VISIBLE

    }
}
