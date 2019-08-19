package com.koraextra.app.ui.mainActivity.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.team_fragment.*
import kotlinx.android.synthetic.main.team_fragment.backImage
import kotlinx.android.synthetic.main.team_fragment.title

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


        mainViewModel.teamIdLiveData.observe(this, Observer {
            viewModel.id = it
        })

        mainViewModel.teamNameLiveData.observe(this, Observer {
            title.text = it
            viewModel.name = it

        })

        mainViewModel.teamLogoLiveData.observe(this, Observer {
            Glide.with(Injector.getApplicationContext()).load(it)
                .into(teamImage)

            viewModel.logo = it
        })

        val pagerAdapter = TeamsViewPagerAdapter(this.childFragmentManager)
        viewPager.adapter = pagerAdapter
//        viewPager.offscreenPageLimit =  3


        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        teamFavouriteImage.setOnClickListener {
            activity?.toast("Added to your Favourites")
        }

    }

}
