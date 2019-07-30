package com.koraextra.app.ui.mainActivity.players

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.koraextra.app.MyApp
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.players.playersDetails.PlayersDetailsFragment
import com.koraextra.app.ui.mainActivity.players.playersMatches.PlayersMatchesFragment
import com.koraextra.app.ui.mainActivity.team.teamLatestNews.TeamLatestNewsFragment
import com.koraextra.app.ui.mainActivity.team.teamMatches.TeamMatchesFragment
import com.koraextra.app.ui.mainActivity.team.teamGroup.TeamGroupFragment
import com.koraextra.app.ui.mainActivity.team.teamPlayers.TeamPlayersFragment

class PlayerViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = listOf(
        PlayersDetailsFragment(),
        PlayersMatchesFragment()
    )

    override fun getItem(position: Int): Fragment =
        fragmentList[position]

    override fun getCount(): Int =
        fragmentList.size

    override fun getItemPosition(`object`: Any): Int {
        return fragmentList.size - count - 1
    }


    override fun getPageTitle(position: Int): String? {
        return when (position) {
            0 -> {
                MyApp.instance.getString(R.string.mateches)
            }
            1 -> {
                MyApp.instance.getString(R.string.details)
            }
            else -> {
                "كورة اكستراا"
            }
        }
    }
}