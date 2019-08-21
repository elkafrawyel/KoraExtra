package com.koraextra.app.ui.mainActivity.match

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.match_fragment.*
import java.util.*

class MatchFragment : Fragment() {

    companion object {
        fun newInstance() = MatchFragment()
    }

    private lateinit var viewModel: MatchViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        viewModel.uiState.observe(this, Observer { onMatchResponse(it) })
        arguments?.let {
            val fixtureId = MatchFragmentArgs.fromBundle(it).fixtureId
            viewModel.fixtureId = fixtureId
            viewModel.getMatch()
        }

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        teamShareImage.setOnClickListener {
            activity?.toast("Added to your Share")
        }

        val pagerAdapter = MatchViewPagerAdapter(this.childFragmentManager)
        viewPager.adapter = pagerAdapter
//        viewPager.offscreenPageLimit =  4

    }

    private fun onMatchResponse(state: MyUiStates?) {
        when (state) {
            MyUiStates.Loading -> {
//                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                onMatchStateSuccess()
            }
            MyUiStates.LastPage -> {
//                loading.visibility = View.GONE
            }
            is MyUiStates.Error -> {
//                loading.visibility = View.GONE
                activity?.snackBar(state.message, matchRootView)
            }
            MyUiStates.NoConnection -> {
//                loading.visibility = View.GONE
                timer.text = context?.resources?.getString(R.string.noConnectionMessage)
                activity?.snackBarWithAction(
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    matchRootView
                ) {
                    viewModel.fixtureId?.let {
                        viewModel.getMatch()
                    }
                }
            }
            MyUiStates.Empty -> {
//                loading.visibility = View.GONE

            }
            null -> {

            }
        }
    }

    private fun onMatchStateSuccess() {
        viewModel.matchLiveData?.observe(this, object : Observer<MatchModel?> {
            override fun onChanged(it: MatchModel?) {
                setMatch(it)
            }
        })
    }

    private fun setMatch(item: MatchModel?) {
        mainViewModel.setMatch(item!!)
        if (item.homeTeam != null && item.awayTeam != null) {
            when (item.statuskey) {
                1, 3 -> {
                    //لم تبدأ
                    homeName.text = item.homeTeam.teamName
                    awayName.text = item.awayTeam.teamName
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImg)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImg)
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImgToolbar)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImgToolbar)

                    timer.text = activity?.getTimeFromMills(item.eventTimestamp!!)
                    timerToolbar.text = activity?.getTimeFromMills(item.eventTimestamp!!)

                    homeScore.text = "-"
                    awayScore.text = "-"
                    homeScoreToolbar.text = "-"
                    awayScoreToolbar.text = "-"


                    matchStatusTv.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.white))
                    matchStatusTv.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                    matchStatusTv.text = context!!.resources.getString(R.string.not_started)
                }

                2, 8, 9 -> {
                    homeName.text = item.homeTeam.teamName
                    awayName.text = item.awayTeam.teamName
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImg)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImg)
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImgToolbar)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImgToolbar)
                    if (item.goalsHomeTeam != null) {
                        homeScore.text = item.goalsHomeTeam.toString()
                        homeScoreToolbar.text = item.goalsHomeTeam.toString()
                    } else {
                        homeScore.text = "0"
                        homeScoreToolbar.text = "0"
                    }
                    if (item.goalsAwayTeam != null) {
                        awayScore.text = item.goalsAwayTeam.toString()
                        awayScoreToolbar.text = item.goalsAwayTeam.toString()
                    } else {
                        awayScore.text = "0"
                        awayScoreToolbar.text = "0"
                    }
                    timer.text = activity?.getTimeFromMills(item.eventTimestamp!!)
                    timerToolbar.text = activity?.getTimeFromMills(item.eventTimestamp!!)

                    matchStatusTv.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.darker_gray))
                    matchStatusTv.setTextColor(ContextCompat.getColor(context!!, android.R.color.white))
                    matchStatusTv.text = context!!.resources.getString(R.string.match_ended)

                }
                4, 5, 6, 7, 10, 18, 19 -> {
                    homeName.text = item.homeTeam.teamName
                    awayName.text = item.awayTeam.teamName
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImg)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImg)
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImgToolbar)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImgToolbar)
                    if (item.goalsHomeTeam != null) {
                        homeScore.text = item.goalsHomeTeam.toString()
                        homeScoreToolbar.text = item.goalsHomeTeam.toString()
                    } else {
                        homeScore.text = "0"
                        homeScoreToolbar.text = "0"
                    }
                    if (item.goalsAwayTeam != null) {
                        awayScore.text = item.goalsAwayTeam.toString()
                        awayScoreToolbar.text = item.goalsAwayTeam.toString()
                    } else {
                        awayScore.text = "0"
                        awayScoreToolbar.text = "0"
                    }



                    timer.base = SystemClock.elapsedRealtime() - activity?.getTimeAgoAsMills(item.eventTimestamp!!)!!
                    timer.setOnChronometerTickListener {
                        val time = SystemClock.elapsedRealtime() - it.base
                        var Seconds = (time / 1000).toInt()
                        val Minutes = Seconds / 60
                        Seconds %= 60
                        var timerText=""
                        if(Minutes>=130){
                            timerText = "130:00"
                        }else{
                            timerText = String.format(Locale("en"),"%02d:%02d", Minutes, Seconds)
                        }
                        it.text = timerText
                        timerToolbar.text = timerText
                    }
                    timer.start()

                    matchStatusTv.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.holo_red_dark))
                    matchStatusTv.setTextColor(ContextCompat.getColor(context!!, android.R.color.white))
                    matchStatusTv.text = context!!.resources.getString(R.string.live)
                }
                11, 12, 13, 14, 15, 16, 17 -> {

                    homeName.text = item.homeTeam.teamName
                    awayName.text = item.awayTeam.teamName
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImg)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImg)
                    Glide.with(Injector.getApplicationContext()).load(item.homeTeam.logo)
                        .into(homeImgToolbar)
                    Glide.with(Injector.getApplicationContext()).load(item.awayTeam.logo)
                        .into(awayImgToolbar)

                    timer.text = activity?.getTimeFromMills(item.eventTimestamp!!)
                    timerToolbar.text = activity?.getTimeFromMills(item.eventTimestamp!!)

                    homeScore.text = "-"
                    awayScore.text = "-"
                    homeScoreToolbar.text = "-"
                    awayScoreToolbar.text = "-"

                    matchStatusTv.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.white))
                    matchStatusTv.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                    matchStatusTv.text = context!!.resources.getString(R.string.not_started)
                }
                else -> {
                    activity?.toast("حاله غير معروفه")
                }
            }
        }
    }

}
