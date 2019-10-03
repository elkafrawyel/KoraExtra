package com.koraextra.app.ui.mainActivity.match.videos

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.chad.library.adapter.base.BaseQuickAdapter

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.MatchVideoModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.match_videos_fragment.*

class MatchVideosFragment : Fragment(), Observer<MatchModel>,
    BaseQuickAdapter.OnItemChildClickListener {
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view?.id){
            R.id.videoItem ->{
                val video = (adapter?.data as List<MatchVideoModel>)[position]
                    context!!.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(video.video)
                        )
                    )
            }
        }
    }

    companion object {
        fun newInstance() = MatchVideosFragment()
    }

    private lateinit var mainViewModel: MainViewModel

    private lateinit var viewModel: MatchVideosViewModel
    private val adapterVideos = AdapterVideos().also {
        it.onItemChildClickListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_videos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchVideosViewModel::class.java)

        if (viewModel.videos.isEmpty()) {
            viewModel.uiState.observe(this, Observer { onVideosResponse(it) })
            mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
            mainViewModel.matchLiveData.observe(this, this)

            videosRv.adapter = adapterVideos
            videosRv.setHasFixedSize(true)
        }
    }

    private fun onVideosResponse(it: MyUiStates?) {
        when (it) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                adapterVideos.replaceData(viewModel.videos)
            }
            MyUiStates.LastPage -> {

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                activity?.toast(it.message)
            }
            MyUiStates.NoConnection -> {

            }
            MyUiStates.Empty -> {
            }
            null -> {
            }
        }
    }

    override fun onChanged(it: MatchModel) {
        viewModel.match = it
        viewModel.getVideos()
    }

}
