package com.koraextra.app.ui.mainActivity.team

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koraextra.app.MyApp
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.team.teamMatches.TeamMatchesFragment
import com.koraextra.app.ui.mainActivity.team.teamPlayers.TeamPlayersFragment

class TeamsViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentList = listOf(
        TeamMatchesFragment(),
        TeamPlayersFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position] as Fragment

    override fun getCount(): Int =
        fragmentList.size

    override fun getPageTitle(position: Int): String? {
        return when (position) {
            0 -> {
                MyApp.instance.getString(R.string.mateches)
            }
            1 -> {
                MyApp.instance.getString(R.string.players)
            }
            else -> {
                "كورة اكستراا"
            }
        }
    }
}