package com.koraextra.app.ui.mainActivity.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.koraextra.app.R
import com.koraextra.app.data.models.FavoriteBody
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBarWithAction
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
        viewModel.uiState.observe(this, Observer<MyUiStates?> { ofFavoriteResponse(it) })
        mainViewModel.teamIdLiveData.observe(this, Observer {
            viewModel.id = it
        })

        mainViewModel.teamNameLiveData.observe(this, Observer {
            title.text = it
            viewModel.name = it
        })
        mainViewModel.leagueIdLiveData.observe(this, Observer {
            viewModel.leagueId = it
        })

        mainViewModel.teamLogoLiveData.observe(this, Observer {
            Glide.with(Injector.getApplicationContext()).load(it)
                .into(teamImage)
            viewModel.logo = it
        })

        mainViewModel.teamFavoLiveData.observe(this, Observer {
            viewModel.favorite = it
            if (it == 0) {
                teamFavouriteImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_unfavorite
                    )
                )
            } else {
                teamFavouriteImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_favorite_white_24dp
                    )
                )
            }
        })

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice("410E806C439261CF851B922E62D371EB")
                .build()
        )

        val pagerAdapter = TeamsViewPagerAdapter(this.childFragmentManager)
        viewPager.adapter = pagerAdapter

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }


        teamFavouriteImage.setOnClickListener {
            //            activity?.toast("Added to your Favourites")
            val preferencesHelper = Injector.getPreferenceHelper()
            if (preferencesHelper.isLoggedIn) {
                if (viewModel.favorite == 0) {
                    viewModel.favorite = 1
                    viewModel.addTeamToFavorite(
                        FavoriteBody(
                            viewModel.name!!,
                            viewModel.id!!,
                            preferencesHelper.id,
                            preferencesHelper.token!!,
                            viewModel.leagueId!!
                        )
                    )
                    teamFavouriteImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_favorite_white_24dp
                        )
                    )
                } else {
                    viewModel.favorite = 0
                    viewModel.removeTeamToFavorite(preferencesHelper.token!!)
                    teamFavouriteImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_unfavorite
                        )
                    )
                }

            } else {
                activity?.snackBarWithAction(
                    getString(R.string.you_must_login),
                    getString(R.string.login),
                    RootView
                ) {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }

    }

    private fun ofFavoriteResponse(it: MyUiStates?) {

        when (it) {
            MyUiStates.Loading -> {
            }
            MyUiStates.Success -> {
                if (viewModel.favorite == 0) {
//                    viewModel
                    activity?.toast(getString(R.string.teamRemovedFromFavorite))

                } else {
                    activity?.toast(getString(R.string.teamAddedToFavorite))
                }
            }
            MyUiStates.LastPage -> {
            }
            is MyUiStates.Error -> {
                activity?.toast(it.message)
            }
            MyUiStates.NoConnection -> {
            }
            MyUiStates.Empty -> {
            }
            null -> {
            }
        }
    }

}
