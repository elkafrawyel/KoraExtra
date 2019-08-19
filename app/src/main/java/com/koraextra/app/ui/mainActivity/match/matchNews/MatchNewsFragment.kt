package com.koraextra.app.ui.mainActivity.match.matchNews

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
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.mainActivity.AdapterNews
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.match_news_fragment.*

class MatchNewsFragment : Fragment(), Observer<MatchModel> {


    companion object {
        fun newInstance() = MatchNewsFragment()
    }

    private lateinit var viewModel: MatchNewsViewModel
    private lateinit var mainViewModel: MainViewModel

    private val adapterNews = AdapterNews().also {
            it.setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.newsViewCl -> {
                        mainViewModel.matchLiveData.removeObserver(this)
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
        return inflater.inflate(R.layout.match_news_fragment, container, false)
    }

    override fun onChanged(it: MatchModel?) {
        viewModel.match = it
        viewModel.getNews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchNewsViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        if(viewModel.newsList.isEmpty()) {
            viewModel.uiState.observe(this, Observer {
                onNewsResponse(it)
            })

            mainViewModel.matchLiveData.observe(this, this)
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

                adapterNews.replaceData(viewModel.newsList.toMutableList())

                loading.visibility = View.GONE
                newsRv.visibility = View.VISIBLE
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
