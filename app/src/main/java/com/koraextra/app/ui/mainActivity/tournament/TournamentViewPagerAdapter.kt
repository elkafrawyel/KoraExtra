package com.koraextra.app.ui.mainActivity.tournament

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koraextra.app.MyApp
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.tournament.matches.TournamentMatchesFragment
import com.koraextra.app.ui.mainActivity.tournament.order.TournamentOrderFragment

class TournamentViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentList = listOf(
        TournamentOrderFragment(),
        TournamentMatchesFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int =
        fragmentList.size

    override fun getPageTitle(position: Int): String? {
        return when (position) {
            0 -> {
                MyApp.instance.getString(R.string.order)
            }
            1 -> {
                MyApp.instance.getString(R.string.mateches)
            }
            else -> {
                "كورة اكستراا"
            }
        }
    }
}