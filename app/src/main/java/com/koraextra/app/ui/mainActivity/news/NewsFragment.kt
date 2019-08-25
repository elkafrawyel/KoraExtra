package com.koraextra.app.ui.mainActivity.news

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest

import com.koraextra.app.R
import kotlinx.android.synthetic.main.news_fragment.*
import kotlinx.android.synthetic.main.news_fragment.newsImage

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        adView.loadAd(
            AdRequest.Builder()
                .addTestDevice("5392457EFAD98BBB3676457D618EBB83")
                .build()
        )
        arguments?.let {
            val title = NewsFragmentArgs.fromBundle(it).title
            val image = NewsFragmentArgs.fromBundle(it).image
            val desc = NewsFragmentArgs.fromBundle(it).desc
            val time = NewsFragmentArgs.fromBundle(it).time

            Glide.with(context!!).load(image).into(newsImage)
            newsHeaderTv.text = title
            newsBodyTv.text = desc
            newsTimeTv.text = time
        }
    }

}
