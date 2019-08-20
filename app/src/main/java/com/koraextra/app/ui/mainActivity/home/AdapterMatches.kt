package com.koraextra.app.ui.mainActivity.home

import android.os.SystemClock
import android.widget.Chronometer
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.getTimeAgoAsMills
import com.koraextra.app.utily.getTimeFromMills
import com.koraextra.app.utily.toast
import java.util.*


class AdapterMatches(data: MutableList<MatchModel>?) : BaseMultiItemQuickAdapter<MatchModel, BaseViewHolder>(data) {


    init {
        //used
        addItemType(1, com.koraextra.app.R.layout.match_not_started_item_view)
        addItemType(3, com.koraextra.app.R.layout.match_not_started_item_view)
        addItemType(13, com.koraextra.app.R.layout.match_not_started_item_view)

        addItemType(2, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(8, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(9, com.koraextra.app.R.layout.match_ended_item_view)

        addItemType(4, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(5, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(6, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(7, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(10, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(18, com.koraextra.app.R.layout.match_live_item_view)
        addItemType(19, com.koraextra.app.R.layout.match_live_item_view)
        //not used
        addItemType(11, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(12, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(14, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(15, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(16, com.koraextra.app.R.layout.match_ended_item_view)
        addItemType(17, com.koraextra.app.R.layout.match_ended_item_view)

    }

    override fun convert(helper: BaseViewHolder?, item: MatchModel?) {

        helper?.addOnClickListener(
            com.koraextra.app.R.id.matchItem,
            com.koraextra.app.R.id.homeImg,
            com.koraextra.app.R.id.homeName,
            com.koraextra.app.R.id.awayImg,
            com.koraextra.app.R.id.awayName
        )

        when (helper?.itemViewType) {
            1, 3 -> {
                helper.setText(com.koraextra.app.R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(com.koraextra.app.R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.awayImg))

                helper.setText(com.koraextra.app.R.id.timeTv, mContext.getTimeFromMills(item?.eventTimestamp!!))
            }

            2, 8, 9 -> {
                helper.setText(com.koraextra.app.R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(com.koraextra.app.R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.awayImg))
                if (item?.goalsHomeTeam != null) {
                    helper.setText(com.koraextra.app.R.id.homeScore, item.goalsHomeTeam.toString())
                } else {
                    helper.setText(com.koraextra.app.R.id.homeScore, "0")
                }
                if (item?.goalsAwayTeam != null) {
                    helper.setText(com.koraextra.app.R.id.awayScore, item.goalsAwayTeam.toString())
                } else {
                    helper.setText(com.koraextra.app.R.id.awayScore, "0")
                }
                helper.setText(com.koraextra.app.R.id.matchTitleTv, item?.status!!)
                helper.setText(com.koraextra.app.R.id.timeTv, mContext.getTimeFromMills(item.eventTimestamp!!))

            }
            4, 5, 6, 7, 10, 18, 19 -> {
                helper.setText(com.koraextra.app.R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(com.koraextra.app.R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.awayImg))
                if (item?.goalsHomeTeam != null) {
                    helper.setText(com.koraextra.app.R.id.homeScore, item.goalsHomeTeam.toString())
                } else {
                    helper.setText(com.koraextra.app.R.id.homeScore, "0")
                }
                if (item?.goalsAwayTeam != null) {
                    helper.setText(com.koraextra.app.R.id.awayScore, item.goalsAwayTeam.toString())
                } else {
                    helper.setText(com.koraextra.app.R.id.awayScore, "0")
                }

                helper.setText(com.koraextra.app.R.id.matchTitleTv, item?.status!!)


                val timer = helper.getView<Chronometer>(com.koraextra.app.R.id.timer)
                timer.base = SystemClock.elapsedRealtime() - mContext.getTimeAgoAsMills(item.eventTimestamp!!)
                timer.setOnChronometerTickListener {
                    val time = SystemClock.elapsedRealtime() - it.base
                    var Seconds = (time / 1000).toInt()
                    val Minutes = Seconds / 60
                    Seconds = Seconds % 60
                    var timer2=""
                    if(Minutes>=130){
                        timer2 = "130:00"
                    }else{
                        timer2 = String.format(Locale("en"),"%02d:%02d", Minutes, Seconds)
                    }
                    it.setText(timer2)
                }
                timer.start()
            }
            11, 12, 13, 14, 15, 16, 17 -> {
                val a: Int = helper.itemViewType
                val b: Int? = item?.fixtureId
                mContext.toast("$a - $b")
                helper.setText(com.koraextra.app.R.id.homeName, item?.homeTeam?.teamName)
                helper.setText(com.koraextra.app.R.id.awayName, item?.awayTeam?.teamName)
                Glide.with(Injector.getApplicationContext()).load(item?.homeTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.homeImg))
                Glide.with(Injector.getApplicationContext()).load(item?.awayTeam?.logo)
                    .into(helper.getView(com.koraextra.app.R.id.awayImg))

                helper.setText(com.koraextra.app.R.id.timeTv, mContext.getTimeFromMills(item?.eventTimestamp!!))
            }
            else -> {
                mContext.toast("حاله غير معروفه")
            }
        }
    }

}