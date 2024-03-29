package com.koraextra.app.ui.mainActivity.match.matchEvents

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.utily.Injector

class AdapterMatchEvents(data: MutableList<EventModel>?) :
    BaseMultiItemQuickAdapter<EventModel, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.match_started_event_item_view)
        addItemType(1, R.layout.match_sub_event_item_view_home)
        addItemType(2, R.layout.match_sub_event_item_view_away)
        addItemType(3, R.layout.match_main_event_item_view)
    }

    override fun convert(helper: BaseViewHolder?, item: EventModel?) {
        helper?.addOnClickListener(R.id.eventItem)
        when (helper?.itemViewType) {
            0 -> {

            }

            1 -> {
                if (item?.youtube == "")
                    helper.setGone(R.id.playVideo, false)
                else
                    helper.setGone(R.id.playVideo,true)

                helper.setText(R.id.eventElapsed, item?.elapsed!!.toString())
                helper.setText(R.id.PlayerName, item.player!!)
                if (item.detail != null)
                    helper.setText(R.id.eventName, item.detail)
                else
                    helper.setText(R.id.eventName, "")

                Glide.with(Injector.getApplicationContext()).load(item.eventimg)
                    .into(helper.getView(R.id.eventImg))
            }

            2 -> {
                if (item?.youtube == "")
                    helper.setGone(R.id.playVideo, false)
                else
                    helper.setGone(R.id.playVideo,true)

                helper.setText(R.id.eventElapsed, item?.elapsed!!.toString())
                helper.setText(R.id.PlayerName, item.player!!)
                if (item.detail != null)
                    helper.setText(R.id.eventName, item.detail)
                else
                    helper.setText(R.id.eventName, "")

                Glide.with(Injector.getApplicationContext()).load(item.eventimg)
                    .into(helper.getView(R.id.eventImg))
            }
        }
    }

}