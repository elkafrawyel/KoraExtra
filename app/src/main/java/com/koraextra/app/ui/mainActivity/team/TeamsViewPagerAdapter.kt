package com.koraextra.app.ui.mainActivity.team

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koraextra.app.MyApp
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.team.teamLatestNews.TeamLatestNewsFragment
import com.koraextra.app.ui.mainActivity.team.teamMatches.TeamMatchesFragment
import com.koraextra.app.ui.mainActivity.team.teamGroup.TeamGroupFragment
import com.koraextra.app.ui.mainActivity.team.teamPlayers.TeamPlayersFragment

class TeamsViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = listOf(
        TeamMatchesFragment(),
        TeamLatestNewsFragment(),
        TeamGroupFragment(),
        TeamPlayersFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int =
        fragmentList.size

    override fun getItemPosition(`object`: Any): Int {
        return fragmentList.size - count - 1
    }



    override fun getPageTitle(position: Int): String? {
        when (position) {
            0 -> {
                return MyApp.instance.getString(R.string.players)
            }
            1 -> {
                return MyApp.instance.getString(R.string.order)
            }
            2 -> {
                return MyApp.instance.getString(R.string.latestNews)
            }
            3 -> {
                return MyApp.instance.getString(R.string.mateches)
            }
            else -> {
                return "كورة اكستراا"
            }
        }
    }
}