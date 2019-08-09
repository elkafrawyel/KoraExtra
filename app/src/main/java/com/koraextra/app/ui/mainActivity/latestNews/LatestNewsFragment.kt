package com.koraextra.app.ui.mainActivity.latestNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.koraextra.app.R
import com.koraextra.app.data.models.NewsModel
import com.koraextra.app.ui.mainActivity.team.teamLatestNews.AdapterNews
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
        newsViewPager.adapter = latestNewsSliderAdapter
        val mainNews = ArrayList<NewsModel>()
        mainNews.add(
            NewsModel(
                "https://fos.cmb.ac.lk/blog/wp-content/uploads/2019/04/Football.jpg",
                "صفقة انتقال بيبي إلى آرسنال تمت وموعد الكشف الطبي يتحدد "
            )
        )
        mainNews.add(
            NewsModel(
                "https://www.raf.mod.uk/aircadets/raf-aircadets/assets/Image/Football.jpg",
                "كوتروني يعلن انضمامه إلى وولفرهامبتون "
            )
        )
        mainNews.add(
            NewsModel(
                "https://images.performgroup.com/di/library/GOAL/f3/95/football-rule-changes_1sq9naux6zqbf13gxeov5833fa.jpg?t=-2038154983&quality=60&w=1408",
                "آخر أخبار صفقات وسوق انتقالات ريال مدريد اليوم 29 / 07 / 2019 "
            )
        )
        mainNews.add(
            NewsModel(
                "https://images.performgroup.com/di/library/GOAL/7f/ea/willian-chelsea-manchester-united-free-kick_1sx72o7v2m2im1cf1kqvboht0r.jpg?t=-2034903303&quality=60&w=1600",
                "خاميس ينضم لتدريبات ريال مدريد بعد عامين من الغياب "
            )
        )

        latestNewsSliderAdapter.submitList(mainNews)
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


//        adapterNews.replaceData(news)

        adapterNews.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.newsViewCl -> {
                    findNavController().navigate(R.id.newsFragment)
                }
            }
        }
        latestNewsRv.adapter = adapterNews
        latestNewsRv.setHasFixedSize(true)
        newsViewPager.setCurrentItem(0, true)
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
