package com.koraextra.app.ui.mainActivity.players.playersDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.koraextra.app.R
import com.koraextra.app.data.models.NewsModel
import kotlinx.android.synthetic.main.players_details_fragment.*

class PlayersDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersDetailsFragment()
    }

    private lateinit var viewModel: PlayersDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersDetailsViewModel::class.java)
        // TODO: Use the ViewModel

        val mainNews = ArrayList<NewsModel>()
        mainNews.add(
            NewsModel(
                "https://upload.wikimedia.org/wikipedia/ar/5/5f/African_Nations_Championship_official_logo.png",
                "كأس أمم افريقيا"
            )
        )


        val spinnerAdapter = ChampionSpinnerAdapter(context!!, mainNews)
        spinnerChampion.adapter = spinnerAdapter

        spinnerChampion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }

    }

}
