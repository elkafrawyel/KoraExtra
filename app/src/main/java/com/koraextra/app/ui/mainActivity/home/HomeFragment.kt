package com.koraextra.app.ui.mainActivity.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.koraextra.app.R
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private val adapterMatches = AdapterMatches()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        navigationView.setNavigationItemSelectedListener(this)

        drawerToggleImgBtn.setOnClickListener {
            //            if(drawerLayout.isDrawerOpen(drawerLayout)){
//                drawerLayout.openDrawer(GravityCompat.END)
//            }else{
            drawerLayout.openDrawer(GravityCompat.START)
//            }
        }

        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(activity!!, drawerLayout, R.string.open, R.string.close) {
                private val scaleFactor = 6f

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val slideX = (drawerView.width * slideOffset * -1)
                    contentHome.translationX = slideX
//                    contentHome.scaleX = 1 - slideOffset / scaleFactor
//                    contentHome.scaleY = 1 - slideOffset / scaleFactor
                }
            }

        drawerLayout.setScrimColor(Color.TRANSPARENT)

//        drawerLayout.setScrimColor(activity!!.resources.getColor(R.color.colorPrimary))
        drawerLayout.drawerElevation = 0f
        drawerLayout.addDrawerListener(actionBarDrawerToggle)


        val socialItemId = 8
        val viewClicked = navigationView.menu.getItem(socialItemId).actionView
        viewClicked.findViewById<LinearLayout>(R.id.facebookView).setOnClickListener {
            activity?.toast("Facebook")
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        animateImage(homeFragmentAppName)

        setUpMatches()

    }

    private fun animateImage(image: ImageView) {

        val rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.rotate)
        image.startAnimation(rotateAnimation)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notification -> {
                activity?.toast("nav_notification")
                findNavController().navigate(R.id.notificationsFragment)
            }
            R.id.nav_favourites -> {
                activity?.toast("nav_favourites")
                findNavController().navigate(R.id.favoritesFragment)
            }
            R.id.nav_newsPaper -> {
                activity?.toast("nav_newsPaper")
                findNavController().navigate(R.id.latestNewsFragment)
            }
            R.id.nav_champions -> {
                activity?.toast("nav_champions")
                findNavController().navigate(
                    R.id.tournamentsFragment
                )
            }
            R.id.nav_TopScorer -> {
                activity?.toast("nav_TopScorer")
                findNavController().navigate(R.id.topScorersFragment)
            }
            R.id.nav_settings -> {
                activity?.toast("nav_settings")
                findNavController().navigate(R.id.settingsFragment)
            }
            R.id.nav_login -> {
                activity?.toast("nav_login")
                findNavController().navigate(R.id.loginFragment)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpMatches(){
        val list = ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")

        adapterMatches.replaceData(list)
        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)
    }
}
