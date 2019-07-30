package com.koraextra.app.ui.mainActivity.players.playersDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.koraextra.app.R
import com.koraextra.app.data.models.NewsModel

class ChampionSpinnerAdapter(val context: Context, var listItemsTxt: ArrayList<NewsModel>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.champion_item_spinner, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

    // setting adapter item height programatically.
     
//        val params = view.layoutParams
//        params.height = 60
//        view.layoutParams = params
        Glide.with(parent!!.context)
            .load(listItemsTxt.get(position).img)
            .into(vh.img)
        vh.label.text = listItemsTxt.get(position).title
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.championLabel) as TextView
        val img: ImageView = row?.findViewById(R.id.championIcon) as ImageView

    }
}