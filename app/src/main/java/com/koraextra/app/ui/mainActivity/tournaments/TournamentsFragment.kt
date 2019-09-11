package com.koraextra.app.ui.mainActivity.tournaments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.gms.ads.AdRequest
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.snackBarWithAction
import kotlinx.android.synthetic.main.tournaments_fragment.*

class TournamentsFragment : Fragment(), BaseQuickAdapter.OnItemChildClickListener {

    companion object {
        fun newInstance() = TournamentsFragment()
    }

    private lateinit var viewModel: TournamentsViewModel
    private lateinit var mainViewModel: MainViewModel
    private val adapterTournament = AdapterTournament().also {
        it.onItemChildClickListener = this
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (view?.id) {
            R.id.tournamentItem -> {
                val tournament = (adapter?.data as List<LeagueModel>)[position]
                mainViewModel.setTournament(tournament)
                val action =
                    TournamentsFragmentDirections
                        .actionTournamentsFragmentToTournamentFragment(
                            tournament.leagueId!!
                            , tournament.logo!!, tournament.name!!
                        )

                findNavController().navigate(action)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournaments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        viewModel.uiState.observe(this, Observer {
            onLeaguesResponse(it)
        })
        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice("5392457EFAD98BBB3676457D618EBB83")
                .build()
        )
        if (viewModel.opened) {
//            viewModel.loadSeasons =false
            val seasonsAdapter = ArrayAdapter(context!!, R.layout.simple_spinner_item, viewModel.seasons)
            seasonsAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            seasonsSpinner.adapter = seasonsAdapter
            seasonsSpinner.setSelection(viewModel.currentSeasonPosition)

            adapterTournament.replaceData(viewModel.leagues.toMutableList())
            tournamentRv.adapter = adapterTournament
            tournamentRv.setHasFixedSize(true)
            tournamentRv.visibility = View.VISIBLE
            seasonsSpinner.visibility = View.VISIBLE
            leaguesTv.visibility = View.VISIBLE
        } else {
            viewModel.opened = true
            viewModel.getSeasons()

        }

        seasonsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (viewModel.chooseThisYear) {
                    viewModel.chooseThisYear = false
                    if (viewModel.currentSeasonPosition != viewModel.seasons.size - 1) {
                        viewModel.currentSeasonPosition = viewModel.seasons.size - 1
                        viewModel.getLeaguesBySeason(viewModel.seasons[viewModel.currentSeasonPosition].toString())
                    }
                } else {
                    if (viewModel.currentSeasonPosition != position) {
                        viewModel.currentSeasonPosition = position
                        viewModel.getLeaguesBySeason(viewModel.seasons[position].toString())
                    }
                }
            }
        }
    }

    private fun onLeaguesResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                tournamentRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
                if (!viewModel.loadSeasons) {
                    seasonsSpinner.visibility = View.GONE
                    leaguesTv.visibility = View.GONE
                }
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                if (viewModel.loadSeasons) {
                    setUpLeagues()
                } else {
                    viewModel.loadSeasons = true
                    setUpSeasons()
                }
            }
            MyUiStates.LastPage -> {
                loading.visibility = View.GONE
                tournamentRv.visibility = View.VISIBLE
                emptyMessageTv.visibility = View.GONE

                seasonsSpinner.visibility = View.GONE
                leaguesTv.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                tournamentRv.visibility = View.GONE
                activity?.snackBar(states.message, rootView)
                emptyMessageTv.visibility = View.GONE

                seasonsSpinner.visibility = View.GONE
                leaguesTv.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {
                loading.visibility = View.GONE
                tournamentRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                seasonsSpinner.visibility = View.GONE
                leaguesTv.visibility = View.GONE
                seasonsSpinner.visibility = View.GONE
                leaguesTv.visibility = View.GONE
                activity?.snackBarWithAction(
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    rootView
                ) {
                    refresh()
                }
            }
            MyUiStates.Empty -> {
                loading.visibility = View.GONE
                tournamentRv.visibility = View.GONE
                emptyMessageTv.visibility = View.VISIBLE

                seasonsSpinner.visibility = View.VISIBLE
                leaguesTv.visibility = View.VISIBLE
            }
            null -> {

            }
        }
    }

    private fun setUpLeagues() {
        adapterTournament.replaceData(viewModel.leagues.toMutableList())
        tournamentRv.adapter = adapterTournament
        tournamentRv.setHasFixedSize(true)
        tournamentRv.visibility = View.VISIBLE
        seasonsSpinner.visibility = View.VISIBLE
        leaguesTv.visibility = View.VISIBLE
    }

    private fun setUpSeasons() {
        val seasonsAdapter = ArrayAdapter(context!!, R.layout.simple_spinner_item, viewModel.seasons)
        seasonsAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        seasonsSpinner.adapter = seasonsAdapter
        seasonsSpinner.setSelection(viewModel.seasons.size - 1)
        seasonsSpinner.visibility = View.VISIBLE
        leaguesTv.visibility = View.VISIBLE
    }

    private fun refresh() {
        if (!viewModel.loadSeasons) {
            viewModel.getSeasons()
        } else {
            viewModel.getLeaguesBySeason(viewModel.seasons[viewModel.currentSeasonPosition].toString())
        }
    }

}
