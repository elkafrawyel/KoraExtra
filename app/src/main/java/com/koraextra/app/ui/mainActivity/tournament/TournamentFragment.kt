package com.koraextra.app.ui.mainActivity.tournament

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.koraextra.app.R
import kotlinx.android.synthetic.main.tournament_fragment.*

class TournamentFragment : Fragment() {

    companion object {
        fun newInstance() = TournamentFragment()
    }

    private lateinit var viewModel: TournamentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournament_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentViewModel::class.java)

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }


        arguments?.let {

            viewModel.tournamentId = TournamentFragmentArgs.fromBundle(it).id

            val name = TournamentFragmentArgs.fromBundle(it).name
            title.text = name
            val image = TournamentFragmentArgs.fromBundle(it).image

            Glide.with(context!!).load(image).into(tournamentImage)


        }

//        adView.loadAd(
//            AdRequest.Builder()
//                .addTestDevice("5392457EFAD98BBB3676457D618EBB83")
//                .build()
//        )

        val pagerAdapter = TournamentViewPagerAdapter(this.childFragmentManager)
        viewPager.adapter = pagerAdapter
//        viewPager.offscreenPageLimit =  4

    }

}
