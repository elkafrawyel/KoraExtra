package com.koraextra.app.ui.mainActivity.match.matchDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.getDateFromMills
import com.koraextra.app.utily.getDateFromString
import com.koraextra.app.utily.getTimeFromMills
import kotlinx.android.synthetic.main.match_details_fragment.*

class MatchDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchDetailsFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private var match: MatchModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        mainViewModel.matchLiveData.observe(this, Observer {
            //            activity?.toast("Match :${it.fixtureId}")
            match = it
            val time:String = activity?.getTimeFromMills(match?.eventTimestamp!!)!!
            val date:String = activity?.getDateFromMills(match?.eventTimestamp!!)!!

            roundTv.text = match?.round.toString()
            venueTv.text = match?.venue.toString()

            timeTv.text = time
            dateTv.text = date


        })
    }

}
