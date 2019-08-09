package com.koraextra.app.ui.mainActivity.tournament

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.home.HomeFragmentDirections
import com.koraextra.app.ui.mainActivity.tournaments.TournamentsFragmentDirections
import com.koraextra.app.utily.toast
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

        selectOrder()

        tournamentTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> selectOrder()

                    1 -> selectMatches()

                    2 -> selectNews()

                }
            }
        })

    }

    private fun selectOrder(){
        activity?.findNavController(R.id.tournamentSubHost)!!.navigate(R.id.tournamentOrderFragment)

    }

    private fun selectMatches(){
        activity?.findNavController(R.id.tournamentSubHost)!!.navigate(R.id.tournamentMatchesFragment)

    }

    private fun selectNews(){
        activity?.findNavController(R.id.tournamentSubHost)!!.navigate(R.id.tournamentNewsFragment)

    }

}
