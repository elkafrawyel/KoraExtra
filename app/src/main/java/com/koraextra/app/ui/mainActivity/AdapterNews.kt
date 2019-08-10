package com.koraextra.app.ui.mainActivity

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel

class AdapterNews : BaseQuickAdapter<KoraNewsModel, BaseViewHolder>(R.layout.news_item_view) {

    override fun convert(helper: BaseViewHolder, item: KoraNewsModel) {

        helper.addOnClickListener(R.id.newsViewCl)
        helper.setText(R.id.newsTime,item.createdAt)
        helper.setText(R.id.newsContent,item.description)
        Glide.with(mContext).load(item.img).into(helper.getView(R.id.newsImage))


    }

}