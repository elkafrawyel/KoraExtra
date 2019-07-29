package com.koraextra.app.ui.mainActivity.notifications

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R

class AdapterNotification: BaseQuickAdapter<String, BaseViewHolder>(R.layout.notification_item_view) {

    override fun convert(helper: BaseViewHolder, item: String) {

//        helper.addOnClickListener(R.id.favouritesCardView)
    }

}