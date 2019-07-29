package com.koraextra.app.ui.mainActivity.match.matchNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.team.teamLatestNews.AdapterNews
import kotlinx.android.synthetic.main.match_news_fragment.*

class MatchNewsFragment : Fragment() {

    companion object {
        fun newInstance() = MatchNewsFragment()
    }

    private lateinit var viewModel: MatchNewsViewModel
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
        // TODO: Use the ViewModel

        val news = ArrayList<String>()
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")
        news.add("a")


        adapterNews.replaceData(news)

        matchNewsRv.adapter = adapterNews
        matchNewsRv.setHasFixedSize(true)
    }

}
