package com.koraextra.app.ui.mainActivity.team.teamMatches

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
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.home.AdapterMatches
import com.koraextra.app.ui.mainActivity.home.HomeFragmentDirections
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.team_matches_fragment.*

class TeamMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = TeamMatchesFragment()
    }

    private lateinit var viewModel: TeamMatchesViewModel
    private lateinit var mainViewModel: MainViewModel
    private val list: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(list)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_matches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamMatchesViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        mainViewModel.teamLiveData.observe(this, Observer {
                        activity?.toast("Match :${it}")
            viewModel.teamId = it
            viewModel.getMatchesList()
        })



        viewModel.uiState.observe(this, object : Observer<MyUiStates?> {
            override fun onChanged(it: MyUiStates?) {
                onMatchesChanged(it)
            }
        })

    }


    private fun onMatchesChanged(state: MyUiStates?) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                viewModel.storedMatchesLiveData?.let { it ->
                    it.observe(this, Observer {
                        onStoredMatchesChanged(it)
                    })
                }
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


    private fun onStoredMatchesChanged(matchesList: List<MatchModel>) {
        setUpMatches(matchesList)
    }



    private fun setUpMatches(list: List<MatchModel>) {
        val matchesList: java.util.ArrayList<MatchModel> = arrayListOf()

        matchesList.clear()
        matchesList.addAll(list)
//        activity?.toast("${matchesList.size}")

        val adapterMatches = AdapterMatches(matchesList)

        adapterMatches.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val match = (adapter.data as List<MatchModel>)[position]
                when (view?.id) {
                    com.koraextra.app.R.id.matchItem -> {

                        val action = HomeFragmentDirections.actionHomeFragmentToMatchFragment(match.fixtureId!!)
                        findNavController().navigate(action)
                    }

                    com.koraextra.app.R.id.homeImg,
                    com.koraextra.app.R.id.homeName -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToTeamFragment(
                            match.homeTeam?.teamId!!,
                            match.homeTeam.teamName!!,
                            match.homeTeam.logo!!
                        )
                        findNavController().navigate(action)
                    }

                    com.koraextra.app.R.id.awayImg,
                    com.koraextra.app.R.id.awayName -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToTeamFragment(
                            match.awayTeam?.teamId!!,
                            match.awayTeam.teamName!!,
                            match.awayTeam.logo!!
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)
        matchesRv.visibility = View.VISIBLE

//        if (!viewModel.opened) {
//      runLayoutAnimationFromBottom(ChannelRv, R.anim.layout_animation_from_top)
        runLayoutAnimationFromBottom(matchesRv, R.anim.layout_animation_from_bottom)
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
