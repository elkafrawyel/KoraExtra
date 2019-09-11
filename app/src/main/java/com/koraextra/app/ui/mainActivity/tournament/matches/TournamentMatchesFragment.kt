package com.koraextra.app.ui.mainActivity.tournament.matches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import com.koraextra.app.ui.mainActivity.home.HomeFragmentDirections
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.addAddsToArray
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.tournament_matches_fragment.*
import kotlinx.android.synthetic.main.tournament_matches_fragment.emptyMessageTv
import kotlinx.android.synthetic.main.tournament_matches_fragment.loading
import java.util.ArrayList

class TournamentMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = TournamentMatchesFragment()
    }

    private lateinit var viewModel: TournamentMatchesViewModel
    private lateinit var mainViewModel: MainViewModel
    private val matchesList: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(matchesList).also {
        it.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter
                                                                                  , view, position ->
            val match = (adapter.data[position] as MatchModel)
            when (view?.id) {
                R.id.matchItem -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToMatchFragment(match.fixtureId!!)
                    val parameters = Bundle()
                    parameters.putInt("fixtureId", match.fixtureId)
                    findNavController().navigate(R.id.matchFragment,parameters)
                }

                R.id.homeImg,
                R.id.homeName -> {
                    mainViewModel.setTeamId(match.homeTeamid!!)
                    mainViewModel.setTeamName(match.homeTeam?.teamName!!)
                    mainViewModel.setTeamLogo(match.homeTeam.logo!!)
                    mainViewModel.setTeamFavo(match.homeTeam.favorite!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)
                }

                R.id.awayImg,
                R.id.awayName -> {
                    mainViewModel.setTeamId(match.awayTeamid!!)
                    mainViewModel.setTeamName(match.awayTeam?.teamName!!)
                    mainViewModel.setTeamLogo(match.awayTeam.logo!!)
                    mainViewModel.setTeamFavo(match.awayTeam.favorite!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournament_matches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentMatchesViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)


        if (viewModel.opened) {
            viewModel.storedMatchesLiveData?.observe(this, Observer {
                setUpMatches(it)
            })

        } else {
            viewModel.opened = true
            mainViewModel.tournamentLiveData.observe(this, Observer {
                viewModel.tournament = it
                viewModel.getMatchesList()
            })

            viewModel.uiState.observe(this,
                Observer<MyUiStates?> { onMatchesChanged(it) })
        }

        tournamentMatchesRv.adapter = adapterMatches
        tournamentMatchesRv.setHasFixedSize(true)
    }

    private fun onMatchesChanged(states: MyUiStates?) {

        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                tournamentMatchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            MyUiStates.Success -> {
                viewModel.storedMatchesLiveData?.let { it ->
                    it.observe(this, Observer {
                        setUpMatches(it)
                    })
                }
            }

            MyUiStates.LastPage -> {
                loading.visibility = View.GONE
                tournamentMatchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                tournamentMatchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                activity?.snackBar(states.message, rootView)
            }

            MyUiStates.NoConnection -> {
                //show no connect view
                loading.visibility = View.GONE
                tournamentMatchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                activity?.snackBar(context?.resources?.getString(R.string.noConnectionMessage), rootView)
            }
            MyUiStates.Empty -> {
                //show Empty view
                emptyMessageTv.visibility = View.VISIBLE
                loading.visibility = View.GONE
                tournamentMatchesRv.visibility = View.GONE

            }
            null -> {
            }
        }
    }

    private fun setUpMatches(list: List<MatchModel>) {
        matchesList.clear()
        activity?.addAddsToArray(list)?.let { matchesList.addAll(it) }

        adapterMatches.notifyDataSetChanged()
        tournamentMatchesRv.visibility = View.VISIBLE
        loading.visibility = View.GONE
        emptyMessageTv.visibility = View.GONE
    }

}
