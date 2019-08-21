package com.koraextra.app.ui.mainActivity.tournament.order

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueTableModel

class AdapterLeagueTable(layoutResId: Int, sectionHeadResId: Int, data: MutableList<LeagueTableModel>?) :
    BaseSectionQuickAdapter<LeagueTableModel, BaseViewHolder>(layoutResId, sectionHeadResId, data) {

    override fun convertHead(helper: BaseViewHolder, item: LeagueTableModel) {
        helper.setText(R.id.tableHeaderTv, item.header)
    }

    override fun convert(helper: BaseViewHolder, item: LeagueTableModel) {
        val groupTeam = item.t
        helper.setText(R.id.order_tv, groupTeam.rank.toString())
        Glide.with(mContext).load(groupTeam.logo).into(helper.getView(R.id.teamInGroupImage))
        helper.setText(R.id.teamNameTv, groupTeam.teamName)
        helper.setText(R.id.played_tv, groupTeam.all?.matchsPlayed.toString())
        helper.setText(R.id.win_tv, groupTeam.all?.win.toString())
        helper.setText(R.id.lose_tv, groupTeam.all?.lose.toString())
        helper.setText(R.id.draw_tv, groupTeam.all?.draw.toString())
        helper.setText(R.id.goals_tv, groupTeam.all?.goalsFor.toString())
        helper.setText(R.id.points_tv, groupTeam.points.toString())
    }

}