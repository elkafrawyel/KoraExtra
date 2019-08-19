package com.koraextra.app.ui.mainActivity.team.teamLatestNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.ui.mainActivity.AdapterNews
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.team_latest_news_fragment.loading
import kotlinx.android.synthetic.main.team_latest_news_fragment.newsRv
import kotlinx.android.synthetic.main.team_latest_news_fragment.rootView

class TeamLatestNewsFragment : Fragment() {

    companion object {
        fun newInstance() = TeamLatestNewsFragment()
    }

    private lateinit var viewModel: TeamLatestNewsViewModel
    private lateinit var mainViewModel: MainViewModel

    private val adapterNews = AdapterNews().also {
        it.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.newsViewCl -> {
                    val news = (adapter.data as List<KoraNewsModel>)[position]
                    (activity as MainActivity).openNewsFragment(
                        news.title!!,
                        news.img!!,
                        news.description!!,
                        news.createdAt!!
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_latest_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamLatestNewsViewModel::class.java)

        if (viewModel.newsList.isEmpty()) {
            viewModel.uiState.observe(this, Observer {
                onNewsResponse(it)
            })

            mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
            mainViewModel.teamIdLiveData.observe(this, Observer {
                viewModel.teamId = it
                viewModel.getNews()
            })
        }
    }

    private fun onNewsResponse(state: MyUiStates) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                newsRv.visibility = View.GONE

            }
            MyUiStates.Success -> {

                adapterNews.replaceData(viewModel.newsList.toMutableList())

                loading.visibility = View.GONE
                newsRv.visibility = View.VISIBLE
                newsRv.adapter = adapterNews
                newsRv.setHasFixedSize(true)
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

}
