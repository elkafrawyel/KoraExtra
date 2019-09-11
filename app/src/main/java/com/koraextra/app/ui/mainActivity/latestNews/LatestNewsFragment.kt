package com.koraextra.app.ui.mainActivity.latestNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.models.NewsModel
import com.koraextra.app.ui.mainActivity.AdapterNews
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import kotlinx.android.synthetic.main.latest_news_fragment.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class LatestNewsFragment : Fragment() {

    companion object {
        fun newInstance() = LatestNewsFragment()
    }

    private lateinit var viewModel: LatestNewsViewModel
    private val latestNewsSliderAdapter = LatestNewsSliderAdapter()
    private val adapterNews = AdapterNews()
    private var timer: Timer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.latest_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LatestNewsViewModel::class.java)
        // TODO: Use the ViewModel

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice("5392457EFAD98BBB3676457D618EBB83")
                .build()
        )
        if (viewModel.opened){
            onSuccess()

        }else {
            viewModel.opened = true
            viewModel.uiState.observe(this, androidx.lifecycle.Observer {
                onNewsResponse(it)
            })

            viewModel.getAllNews()
        }

        adapterNews.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.newsViewCl -> {
                    val news = (adapter.data as List<KoraNewsModel>)[position]
                    val action = LatestNewsFragmentDirections
                        .actionLatestNewsFragmentToNewsFragment(news.title!!,
                            news.img!!,
                            news.description!!,
                            news.createdAt!!)
                    findNavController().navigate(action)
                }
            }
        }

        latestNewsRv.adapter = adapterNews
        latestNewsRv.setHasFixedSize(true)
        newsViewPager.setCurrentItem(0, true)
    }

    private fun onNewsResponse(state: MyUiStates) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                latestNewsRv.visibility = View.GONE

            }
            MyUiStates.Success -> {

                onSuccess()
            }
            MyUiStates.LastPage -> {

                loading.visibility = View.GONE
                latestNewsRv.visibility = View.GONE
            }
            is MyUiStates.Error -> {
                activity?.snackBar(state.message, rootView)
                loading.visibility = View.GONE
                latestNewsRv.visibility = View.GONE
            }
            MyUiStates.NoConnection -> {

                loading.visibility = View.GONE
                latestNewsRv.visibility = View.GONE
            }
            MyUiStates.Empty -> {

                loading.visibility = View.GONE
                latestNewsRv.visibility = View.GONE
            }
        }
    }

    private fun onSuccess() {
        adapterNews.replaceData(viewModel.newsList.toMutableList())

        loading.visibility = View.GONE
        latestNewsRv.visibility = View.VISIBLE
        latestNewsRv.adapter = adapterNews
        latestNewsRv.setHasFixedSize(true)


        newsViewPager.adapter = latestNewsSliderAdapter
        val mainNews = ArrayList<KoraNewsModel>()

        try {
            mainNews.addAll(viewModel.newsList.subList(0,5).toMutableList())
            latestNewsSliderAdapter.submitList(mainNews)
        }catch (ex :Exception){
            newsViewPager.visibility = View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask {
            requireActivity().runOnUiThread {
                if (newsViewPager != null) {
                    if (newsViewPager.currentItem < latestNewsSliderAdapter.count - 1) {
                        newsViewPager.setCurrentItem(newsViewPager.currentItem + 1, true)
                    } else {
                        newsViewPager.setCurrentItem(0, true)
                    }
                }
            }
        }, 5000, 5000)
    }

    override fun onPause() {
        timer?.cancel()
        super.onPause()
    }

}
