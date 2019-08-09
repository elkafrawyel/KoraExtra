package com.koraextra.app.ui.mainActivity.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.team_fragment.*

class TeamFragment : Fragment() {

    companion object {
        fun newInstance() = TeamFragment()
    }

    private lateinit var viewModel: TeamViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        arguments?.let {
            val teamid = TeamFragmentArgs.fromBundle(it).teamid
            val name = TeamFragmentArgs.fromBundle(it).name
            val logo = TeamFragmentArgs.fromBundle(it).logo

            mainViewModel.setTeamId(teamid)

            title.text = name
            Glide.with(Injector.getApplicationContext()).load(logo)
                .into(teamImage)
        }

//        activity?.findNavController(R.id.teamFragment)?.navigate(R.id.teamMatchesFragment)

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        teamFavouriteImage.setOnClickListener {
            activity?.toast("Added to your Favourites")
        }

//        view_pager.rotationY = 180F
        TeamsViewPagerAdapter(fragmentManager!!).also {
            teamsTabs.setupWithViewPager(view_pager)
            view_pager.adapter = it
        }

    }

}
