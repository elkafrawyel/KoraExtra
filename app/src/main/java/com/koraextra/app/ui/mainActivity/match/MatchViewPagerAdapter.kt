package com.koraextra.app.ui.mainActivity.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koraextra.app.MyApp
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.match.matchDetails.MatchDetailsFragment
import com.koraextra.app.ui.mainActivity.match.matchEvents.MatchEventsFragment
import com.koraextra.app.ui.mainActivity.match.matchNews.MatchNewsFragment
import com.koraextra.app.ui.mainActivity.match.matchTops.MatchTopsFragment
import com.koraextra.app.ui.mainActivity.team.teamLatestNews.TeamLatestNewsFragment
import com.koraextra.app.ui.mainActivity.team.teamMatches.TeamMatchesFragment
import com.koraextra.app.ui.mainActivity.team.teamGroup.TeamGroupFragment
import com.koraextra.app.ui.mainActivity.team.teamPlayers.TeamPlayersFragment

class MatchViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentList = listOf(
        MatchEventsFragment(),
        MatchDetailsFragment(),
        MatchTopsFragment(),
        MatchNewsFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int =
        fragmentList.size

    override fun getPageTitle(position: Int): String? {
        when (position) {
            0 -> {
                return MyApp.instance.resources.getString(R.string.match_events)
            }
            1 -> {
                return MyApp.instance.resources.getString(R.string.match_details)
            }
            2 -> {
                return MyApp.instance.resources.getString(R.string.players)
            }
            3 -> {
                return MyApp.instance.resources.getString(R.string.newsPaper)
            }
            else -> {
                return "كورة اكستراا"
            }
        }
    }
}