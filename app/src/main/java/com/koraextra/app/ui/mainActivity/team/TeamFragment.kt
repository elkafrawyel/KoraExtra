package com.koraextra.app.ui.mainActivity.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.team_fragment.*
import kotlinx.android.synthetic.main.team_fragment.backImage
import kotlinx.android.synthetic.main.team_fragment.title
import kotlinx.android.synthetic.main.tournament_fragment.*

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
            viewModel.id = TeamFragmentArgs.fromBundle(it).teamid
            viewModel.name = TeamFragmentArgs.fromBundle(it).name
            viewModel.logo = TeamFragmentArgs.fromBundle(it).logo

            mainViewModel.setTeamId(viewModel.id!!)

            title.text = viewModel.name
            Glide.with(Injector.getApplicationContext()).load(viewModel.logo)
                .into(teamImage)
        }

        if (viewModel.opened){
            teamsTabs.getTabAt(viewModel.selectedTab)?.select()
        }else{
            viewModel.opened = true
            selectTab(0)
        }

//        activity?.findNavController(R.id.teamFragment)?.navigate(R.id.teamMatchesFragment)

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        teamFavouriteImage.setOnClickListener {
            activity?.toast("Added to your Favourites")
        }

//        view_pager.rotationY = 180F

        teamsTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> selectMatches()

                    1 -> selectLatestNews()

                    2 -> selectplayers()

                }
            }
        })

        selectMatches()
    }


    private fun selectTab(position: Int) {
        when (position) {
            0 -> selectMatches()

            1 -> selectLatestNews()

            2 -> selectplayers()
        }
    }

    private fun selectMatches(){
        val bundle = Bundle()
        bundle.putInt("teamid", viewModel.id!!)
        bundle.putString("name", viewModel.name!!)
        bundle.putString("logo", viewModel.logo!!)
//        view.findNavController().navigate(R.id.fragmentBtoC, bundle)
//        val action =TeamFragmentDirections.actionTeamFragmentToTeamMatchesFragment(viewModel.id!!,viewModel.name!!,viewModel.logo!!)
        activity?.findNavController(R.id.teamSubHost)!!.navigate(R.id.teamMatchesFragment,bundle)

    }

    private fun selectLatestNews(){
        activity?.findNavController(R.id.teamSubHost)!!.navigate(R.id.teamLatestNewsFragment)

    }

    private fun selectplayers(){
        activity?.findNavController(R.id.teamSubHost)!!.navigate(R.id.teamPlayersFragment)

    }

}
