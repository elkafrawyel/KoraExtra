package com.koraextra.app.ui.mainActivity.notifications

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.NotificationModel

class AdapterNotification: BaseQuickAdapter<NotificationModel, BaseViewHolder>(R.layout.notification_item_view) {

    override fun convert(helper: BaseViewHolder, item: NotificationModel) {
        helper.setText(R.id.teamNewsContent,item.notiText)
        helper.setText(R.id.teamsNewsTime,item.updatedAt)
        Glide.with(mContext).load(item.notimg).into(helper.getView(R.id.notificationImg))
    }

}