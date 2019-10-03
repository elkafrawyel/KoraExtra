package com.koraextra.app.ui.mainActivity.match.videos

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchVideoModel

class AdapterVideos :
    BaseQuickAdapter<MatchVideoModel, BaseViewHolder>(R.layout.match_video_item_view) {

    override fun convert(helper: BaseViewHolder, item: MatchVideoModel) {
        Glide.with(mContext).load(item.video).into(helper.getView(R.id.videoImage))
        helper.setText(R.id.videoTitle,item.title)

        helper.addOnClickListener(R.id.videoItem)
    }

}