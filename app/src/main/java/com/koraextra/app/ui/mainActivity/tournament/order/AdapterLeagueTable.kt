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

        helper.addOnClickListener(R.id.teamRow)

        helper.setText(R.id.order_tv, groupTeam.rank.toString())
        Glide.with(mContext).load(groupTeam.logo).into(helper.getView(R.id.teamInGroupImage))
        helper.setText(R.id.teamNameTv, groupTeam.teamName)

        var matchsPlayed =  groupTeam.all?.matchsPlayed.toString()
        if(matchsPlayed.length == 1) matchsPlayed = "  $matchsPlayed"
        helper.setText(R.id.played_tv, matchsPlayed)

        var win =  groupTeam.all?.win.toString()
        if(win.length == 1) win = "  $win"
        helper.setText(R.id.win_tv, win)

        var lose =  groupTeam.all?.lose.toString()
        if(lose.length == 1) lose = "  $lose"
        helper.setText(R.id.lose_tv, lose)

        var draw =  groupTeam.all?.draw.toString()
        if(draw.length == 1) draw = "  $draw"
        helper.setText(R.id.draw_tv, draw)

        var goalsFor =  groupTeam.all?.goalsFor.toString()
        if(goalsFor.length == 1) goalsFor = "  $goalsFor"
        helper.setText(R.id.goals_tv, goalsFor)

        var points =  groupTeam.points.toString()
        if(points.length == 1) points = "  $points"
        helper.setText(R.id.points_tv, points)
    }

}