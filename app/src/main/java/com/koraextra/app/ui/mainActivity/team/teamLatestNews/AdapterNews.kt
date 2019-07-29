package com.koraextra.app.ui.mainActivity.team.teamLatestNews

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R

class AdapterNews : BaseQuickAdapter<String, BaseViewHolder>(R.layout.team_news_item_view) {

    override fun convert(helper: BaseViewHolder, item: String) {

        helper.addOnClickListener(R.id.newsViewCl)

    }

}