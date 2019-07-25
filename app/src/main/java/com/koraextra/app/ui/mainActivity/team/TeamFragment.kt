package com.koraextra.app.ui.mainActivity.team

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

import com.koraextra.app.R
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.team_fragment.*

class TeamFragment : Fragment() {

    companion object {
        fun newInstance() = TeamFragment()
    }

    private lateinit var viewModel: TeamViewModel
    private lateinit var mPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)

        title.text = "ليفربول"

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
