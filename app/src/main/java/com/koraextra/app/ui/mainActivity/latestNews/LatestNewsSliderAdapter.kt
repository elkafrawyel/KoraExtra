package com.koraextra.app.ui.mainActivity.latestNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.models.NewsModel

class LatestNewsSliderAdapter : PagerAdapter() {

    private val newsList = ArrayList<KoraNewsModel>()

    override fun isViewFromObject(view: View, imgv: Any): Boolean {
        return view == imgv as ConstraintLayout
    }

    override fun getCount(): Int {
        return newsList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.news_slider_item, container, false) as ConstraintLayout

        container.addView(view)

        Glide.with(container.context)
            .load(newsList[position].img)
            .into(view.findViewById(R.id.newsImage))

        view.findViewById<TextView>(R.id.newsTitleTv).text= newsList[position].title

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    fun submitList(imagesList: List<KoraNewsModel>) {
        newsList.clear()
        newsList.addAll(imagesList)
        notifyDataSetChanged()
    }

}