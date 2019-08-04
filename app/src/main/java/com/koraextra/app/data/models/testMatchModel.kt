package com.koraextra.app.data.models


import com.chad.library.adapter.base.entity.MultiItemEntity


class testMatchModel(
    val type: Int
) : MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }

}