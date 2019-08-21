package com.koraextra.app.ui.mainActivity.team.teamGroup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.GroupTeam
import com.koraextra.app.data.models.LeagueTableModel

class AdapterTeamGroup: BaseQuickAdapter<String, BaseViewHolder>(R.layout.favorite_item_view) {

    override fun convert(helper: BaseViewHolder, item: String) {

        helper.addOnClickListener(R.id.favouritesCardView)
    }

}