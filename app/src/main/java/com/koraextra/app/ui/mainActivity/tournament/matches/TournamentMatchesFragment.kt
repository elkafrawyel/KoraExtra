package com.koraextra.app.ui.mainActivity.tournament.matches

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import com.koraextra.app.ui.mainActivity.home.HomeFragmentDirections
import com.koraextra.app.ui.mainActivity.tournament.TournamentFragment
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
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

        mainViewModel.tournamentLiveData.observe(this, Observer {
            viewModel.tournament = it
            viewModel.getMatchesList()

        })

        if (viewModel.opened) {
            viewModel.getMatchesList()
        } else {
            viewModel.opened = true
        }
        viewModel.uiState.observe(this,
            Observer<MyUiStates?> { onMatchesChanged(it) })

    }

    private fun onMatchesChanged(states: MyUiStates?) {

        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                tournamentMatchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                viewModel.storedMatchesLiveData?.let { it ->
                    it.observe(this@TournamentMatchesFragment, Observer {
                        onStoredMatchesChanged(it)
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

    private fun onStoredMatchesChanged(matchesList: List<MatchModel>) {
        setUpMatches(matchesList)
    }

    private fun setUpMatches(list: List<MatchModel>) {
        val matchesList: ArrayList<MatchModel> = arrayListOf()

        matchesList.clear()
        matchesList.addAll(list)
        val adapterMatches = AdapterMatches(matchesList)

        adapterMatches.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    R.id.matchItem -> {
                        try {
                            val match = (adapter.data as List<MatchModel>)[position]
                            (activity as MainActivity).openMatchFragment(match.fixtureId!!)
                        } catch (e: IllegalArgumentException) {
                            // User tried tapping 2 links at once!
                            activity?.toast("Can't open Match Fragment!")
                        }
                    }
                }
            }
        tournamentMatchesRv.adapter = adapterMatches
        tournamentMatchesRv.setHasFixedSize(true)
        tournamentMatchesRv.visibility = View.VISIBLE

//        if (!viewModel.opened) {
//      runLayoutAnimationFromBottom(ChannelRv, R.anim.layout_animation_from_top)
        runLayoutAnimationFromBottom(tournamentMatchesRv, R.anim.layout_animation_from_bottom)
//      runLayoutAnimationFromBottom(ChannelRv, R.anim.layout_animation_from_right)
//        }
    }

    private fun runLayoutAnimationFromBottom(
        recyclerView: RecyclerView,
        layout_animation: Int
    ) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, layout_animation)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()

    }

}
