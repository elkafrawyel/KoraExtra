package com.koraextra.app.ui.mainActivity.players

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.koraextra.app.R
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import kotlinx.android.synthetic.main.players_fragment.*
import kotlinx.android.synthetic.main.players_fragment.backImage

class PlayersFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersFragment()
    }

    private lateinit var viewModel: PlayersViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        mainViewModel.playerLiveData.observe(this, Observer {
            val player = it
            playerNameTv.text = player.playerName
            player.number?.let {
                playerNumberTv.text = player.number.toString()
            }
            player.age?.let {
                playerAgeTv.text = player.age.toString() + " عام"
            }
            playerPositionTv.text = player.position.toString()
            playerTeamNameTv.text = player.teamName.toString()
            playerNationNameTv.text = player.nationality.toString()


            Glide.with(this).load(player.playerimage).into(playerImg)
            Glide.with(this).load(player.teamimage).into(playerClubImg)
        })

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }
        val pagerAdapter = PlayerViewPagerAdapter(this.childFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 2


    }

    fun changeState() {
        val motionLayout = motionLayout as? MotionLayout ?: return
        if (motionLayout.progress > 0.5f) {
            motionLayout.transitionToStart()
        } else {
            motionLayout.transitionToEnd()
        }
    }
}
