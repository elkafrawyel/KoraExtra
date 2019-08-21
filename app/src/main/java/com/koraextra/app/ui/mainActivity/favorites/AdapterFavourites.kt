package com.koraextra.app.ui.mainActivity.favorites

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.koraextra.app.R
import com.koraextra.app.data.models.FavoriteModel
import com.koraextra.app.utily.Injector

class AdapterFavourites :
    BaseQuickAdapter<FavoriteModel, BaseViewHolder>(R.layout.favorite_item_view) {

    override fun convert(helper: BaseViewHolder, item: FavoriteModel) {
        helper.setText(R.id.teamName, item.teamName)

        helper.addOnClickListener(R.id.favouritesCardView)
        helper.addOnClickListener(R.id.removeFromFavorite)
        Glide.with(Injector.getApplicationContext()).load(item.teamLogo)
            .into(helper.getView(R.id.teamImg))
    }

}