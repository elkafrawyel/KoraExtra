package com.koraextra.app.ui.mainActivity.team.teamPlayers

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.Player

class AdapterPlayers: BaseQuickAdapter<Player, BaseViewHolder>(R.layout.player_in_team_item_view) {

    override fun convert(helper: BaseViewHolder, item: Player) {
        item.number?.let {

            helper.setText(R.id.playerOrder_tv,it.toString())
        }
        helper.setText(R.id.playerNameTv,item.playerName.toString())
        helper.setText(R.id.playerNationTv,item.teamName!!.toString())
        helper.setText(R.id.player_goals_tv,item.goals?.total!!.toString())
        helper.setText(R.id.playerFreeKiksScoredTv,item.penalty?.success!!.toString())
        helper.setText(R.id.playerFreeKiksMissedTv,item.penalty.missed!!.toString())

        Glide.with(mContext).load(item.playerimage).into(helper.getView(R.id.playerTeamInGroupImage))
    }

}