package com.koraextra.app.ui.mainActivity.tournament.news

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.AdapterNews
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.tournament_news_fragment.*

class TournamentNewsFragment : Fragment() {

    companion object {
        fun newInstance() = TournamentNewsFragment()
    }

    private lateinit var viewModel: TournamentNewsViewModel
    private lateinit var mainViewModel: MainViewModel

    private val adapterNews = AdapterNews().also {
        it.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.newsViewCl -> {
                    val news = (adapter.data as List<KoraNewsModel>)[position]
                    val bundle = bundleOf(
                        "title" to news.title!!,
                        "image" to news.img!!,
                        "desc" to news.description!!,
                        "time" to news.createdAt!!
                    )

                    activity?.findNavController(R.id.fragment)!!.navigate(R.id.newsFragment, bundle)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tournament_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TournamentNewsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        if (viewModel.opened){

        }else {
            viewModel.opened = true
            viewModel.uiState.observe(this, Observer {
                onNewsResponse(it)
            })
            mainViewModel.tournamentLiveData.observe(this, Observer {
                activity?.toast("Name ${it.name}")
                viewModel.leagueModel = it
                viewModel.getNews()
            })
        }


        newsRv.adapter = adapterNews
        newsRv.setHasFixedSize(true)
    }

    private fun onNewsResponse(state: MyUiStates) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                newsRv.visibility = View.GONE

            }
            MyUiStates.Success -> {

                onSuccess()
            }
            MyUiStates.LastPage -> {

                loading.visibility = View.GONE
                newsRv.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                activity?.snackBar(state.message, rootView)
                loading.visibility = View.GONE
                newsRv.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {

                loading.visibility = View.GONE
                newsRv.visibility = View.GONE
            }
            MyUiStates.Empty -> {

                loading.visibility = View.GONE
                newsRv.visibility = View.GONE
            }
        }
    }

    private fun onSuccess() {
        adapterNews.replaceData(viewModel.newsList.toMutableList())

        loading.visibility = View.GONE
        newsRv.visibility = View.VISIBLE
    }

}
