package com.koraextra.app.ui.mainActivity.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.gms.ads.AdRequest

import com.koraextra.app.R
import com.koraextra.app.data.models.FavoriteModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBarWithAction
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private val adapterFavourites = AdapterFavourites()

    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        viewModel.uiState.observe(this, Observer { OnFavoriteResponse(it) })
        viewModel.uiStateFavo.observe(this, Observer<MyUiStates?> { ofFavoriteRemoved(it) })

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice("5392457EFAD98BBB3676457D618EBB83")
                .build()
        )

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.getFavoritesList()

    }

    private fun ofFavoriteRemoved(it: MyUiStates?) {
        when (it) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                activity?.toast(getString(R.string.teamRemovedFromFavorite))
            }
            MyUiStates.LastPage -> {
            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
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

    private fun OnFavoriteResponse(it: MyUiStates?) {
        when (it) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                viewModel.favoritesList?.let { it1 -> adapterFavourites.replaceData(it1) }
                adapterFavourites.onItemChildClickListener =
                    BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                        val team = adapter.data[position] as FavoriteModel
                        when (view?.id) {
                            R.id.favouritesCardView -> {
                                mainViewModel.setTeamId(team.teamId!!.toInt())
                                mainViewModel.setTeamName(team.teamName!!)
                                mainViewModel.setTeamLogo(team.teamLogo!!)
                                mainViewModel.setTeamFavo(1)
                                mainViewModel.setLeagueId(1)
                                findNavController().navigate(R.id.teamFragment)
                            }
                            R.id.removeFromFavorite -> {
                                adapter.remove(position)
                                viewModel.removeTeamToFavorite(
                                    team,
                                    Injector.getPreferenceHelper().token!!
                                )
                            }
                        }
                    }
                favouritesRv.adapter = adapterFavourites
                favouritesRv.setHasFixedSize(true)
            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {
                loading.visibility = View.GONE
                activity?.snackBarWithAction(
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    RootView
                ) {
                    viewModel.getFavoritesList()
                }
            }
            MyUiStates.Empty -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.VISIBLE
            }
            null -> {
            }
        }
    }

}