package com.koraextra.app.ui.mainActivity.match.matchNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.AdapterNews
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.match_news_fragment.*

class MatchNewsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchNewsFragment()
    }

    private lateinit var viewModel: MatchNewsViewModel
    private lateinit var mainViewModel: MainViewModel

    private val adapterNews = AdapterNews()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchNewsViewModel::class.java)
        viewModel.uiState.observe(this, Observer {
            onNewsResponse(it)
        })

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        mainViewModel.matchLiveData.observe(this, Observer {
            activity?.toast("Name ${it.fixtureId}")
            viewModel.match = it
            viewModel.getNews()
        })

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
                activity?.snackBar(state.message,rootView)
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
