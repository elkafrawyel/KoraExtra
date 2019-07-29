package com.koraextra.app.ui.mainActivity.match.matchEvents

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchEventsModel

class AdapterMatchEvents (data: MutableList<MatchEventsModel>?) : BaseMultiItemQuickAdapter<MatchEventsModel, BaseViewHolder>(data) {


    init {
        addItemType(0, R.layout.match_started_event_item_view)
        addItemType(1, R.layout.match_sub_event_item_view_home)
        addItemType(2, R.layout.match_sub_event_item_view_away)
        addItemType(3, R.layout.match_main_event_item_view)
    }

    override fun convert(helper: BaseViewHolder?, item: MatchEventsModel?) {

        when (helper?.itemViewType) {
            0 -> {

            }

            1 -> {

            }

            2 -> {

            }
        }
    }

}