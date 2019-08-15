package com.koraextra.app.ui.mainActivity.players.playersDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer

import com.koraextra.app.R
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.players_details_fragment.*

class PlayersDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersDetailsFragment()
    }
    val players : ArrayList<PlayerModel> = arrayListOf()
    private lateinit var viewModel: PlayersDetailsViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersDetailsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        viewModel.uiState.observe(this, Observer { onPlayerResponse(it) })
        viewModel.playerId = 276
        viewModel.getPlayerList()



    }

    private fun onPlayerResponse(it: MyUiStates?) {
        when(it){
            MyUiStates.Loading -> {}
            MyUiStates.Success -> {

                players.clear()
                players.addAll(viewModel.players!!.toMutableList())
                val spinnerAdapter = ChampionSpinnerAdapter(context!!, players)
                spinnerChampion.adapter = spinnerAdapter

                spinnerChampion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val player = viewModel.players!![position]

                        setPlayer(player)
                        mainViewModel.setPlayer(player)
                    }

                }
            }
            MyUiStates.LastPage -> {}
            is MyUiStates.Error -> {}
            MyUiStates.NoConnection -> {}
            MyUiStates.Empty -> {}
            null -> {}
        }
    }

    private fun setPlayer(player: PlayerModel) {
        activity?.toast(player.toString())

    }

}
