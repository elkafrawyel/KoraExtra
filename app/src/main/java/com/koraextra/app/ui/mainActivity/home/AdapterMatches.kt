package com.koraextra.app.ui.mainActivity.home

import android.util.Log
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.testMatchModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.toast


class AdapterMatches(data: MutableList<MatchModel>?) : BaseMultiItemQuickAdapter<MatchModel, BaseViewHolder>(data) {


    init {
        //used
        addItemType(1, R.layout.match_not_started_item_view)

        addItemType(2, R.layout.match_ended_item_view)
        addItemType(8, R.layout.match_ended_item_view)
        addItemType(9, R.layout.match_ended_item_view)

        addItemType(3, R.layout.match_live_item_view)
        addItemType(4, R.layout.match_live_item_view)
        addItemType(5, R.layout.match_live_item_view)
        addItemType(6, R.layout.match_live_item_view)
        addItemType(7, R.layout.match_live_item_view)
        addItemType(10, R.layout.match_live_item_view)
        addItemType(18, R.layout.match_live_item_view)
        addItemType(19, R.layout.match_live_item_view)
        //not used
        addItemType(11, R.layout.match_ended_item_view)
        addItemType(12, R.layout.match_ended_item_view)
        addItemType(13, R.layout.match_ended_item_view)
        addItemType(14, R.layout.match_ended_item_view)
        addItemType(15, R.layout.match_ended_item_view)
        addItemType(16, R.layout.match_ended_item_view)
        addItemType(17, R.layout.match_ended_item_view)

    }

    override fun convert(helper: BaseViewHolder?, item: MatchModel?) {

        helper?.addOnClickListener(R.id.matchItem)

        when (helper?.itemViewType) {
            1 -> {
                helper.setText(R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(R.id.awayImg))
            }

            2, 8, 9 -> {
                helper.setText(R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(R.id.awayImg))
                helper.setText(R.id.homeScore,item?.goalsHomeTeam!!.toString())
                helper.setText(R.id.awayScore,item.goalsAwayTeam!!.toString())
                helper.setText(R.id.matchTitleTv,item.status!!)

            }
            3, 4, 5, 6, 7, 10, 18,19 -> {
                helper.setText(R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(R.id.awayImg))
                helper.setText(R.id.homeScore,item?.goalsHomeTeam!!.toString())
                helper.setText(R.id.awayScore,item.goalsAwayTeam!!.toString())
                helper.setText(R.id.matchTitleTv,item.status!!)

            }
            11,12,13,14,15,16,17 -> {
                val a:Int = helper.itemViewType
                val b: Int? = item?.fixtureId
                mContext.toast("$a - $b")
                Log.e("koraGol","$a - $b")
            }
            else -> {
                mContext.toast("حاله غير معروفه")
            }
        }
    }

}