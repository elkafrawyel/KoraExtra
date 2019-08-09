package com.koraextra.app.ui.mainActivity.tournaments

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel

class AdapterTournament : BaseQuickAdapter<LeagueModel, BaseViewHolder>(R.layout.champion_item_view) {

    override fun convert(helper: BaseViewHolder, item: LeagueModel) {

        Glide.with(mContext).load(item.logo).into(helper.getView(R.id.championImage))
        helper.setText(R.id.championName, item.name)

        helper.addOnClickListener(R.id.tournamentItem)
    }

}