package com.koraextra.app.ui.mainActivity.home

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel


class AdapterMatches(data: MutableList<MatchModel>?) : BaseMultiItemQuickAdapter<MatchModel, BaseViewHolder>(data) {


    init {
        addItemType(0, R.layout.match_not_started_item_view)
        addItemType(1, R.layout.match_live_item_view)
        addItemType(2, R.layout.match_ended_item_view)
    }

    override fun convert(helper: BaseViewHolder?, item: MatchModel?) {
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