package com.koraextra.app.ui.mainActivity.home

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cobonee.app.utily.MyUiStates
import com.google.android.material.navigation.NavigationView
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private val matchesList: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(matchesList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.koraextra.app.R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.uiState.observe(this, object : Observer<MyUiStates?> {
            override fun onChanged(it: MyUiStates?) {
                onMatchesChanged(it)
            }
        })
        navigationView.setNavigationItemSelectedListener(this)

        drawerToggleImgBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                activity!!,
                drawerLayout,
                com.koraextra.app.R.string.open,
                com.koraextra.app.R.string.close
            ) {
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
        viewClicked.findViewById<LinearLayout>(com.koraextra.app.R.id.facebookView).setOnClickListener {
            activity?.toast("Facebook")
            drawerLayout.closeDrawer(GravityCompat.START)
        }

//        animateView(homeFragmentAppName)

        date_tv.setOnClickListener {
            openDatePicker()
        }

        dayName_tv.setOnClickListener {
            openDatePicker()
        }

        //start with current date
        val date = activity?.getCurrentDate()
        viewModel.date = date
        dayName_tv.text = activity?.getDayName(date!!)
        date_tv.text = activity?.getDateStringFromString(date!!)

        // get normal matches with above date
        viewModel.getMatchesList()

        liveSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //today live
                viewModel.getMatchesList(true)
                linearHeader.visibility = View.GONE
            } else {
                //today normal
                viewModel.getMatchesList()
                linearHeader.visibility = View.VISIBLE

            }
        }

        nextImg.setOnClickListener {
            changeDay(true)
        }

        previousImg.setOnClickListener {
            changeDay()
        }
    }

    private fun changeDay(next:Boolean = false) {
        val date = activity?.getDateFromString(viewModel.date!!)
        val c = Calendar.getInstance()
        c.setTime(date)
        if(next) {
            c.add(Calendar.DATE, 1)
        }else{
            c.add(Calendar.DATE, -1)
        }
        val timeFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val dateString = timeFormat.format(c.time)
        viewModel.date = dateString
        dayName_tv.text = activity?.getDayName(dateString)
        date_tv.text = activity?.getDateStringFromString(dateString)
        viewModel.getMatchesList()
    }



    private fun openDatePicker() {
        val date = activity?.getDateFromString(viewModel.date!!)
        val c = Calendar.getInstance()
        c.time = date
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val date = "$year-${monthOfYear + 1}-$dayOfMonth"
                activity?.toast(date)


                viewModel.date = date
                viewModel.getMatchesList()

                dayName_tv.text = activity?.getDayName(date)
                date_tv.text = activity?.getDateStringFromString(date)

            }, year, month, day
        )

        datePicker.show()
    }

    private fun onMatchesChanged(state: MyUiStates?) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                matchesRv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                matchesRv.visibility = View.VISIBLE
                viewModel.storedMatchesLiveData?.let {
                    it.observe(this@HomeFragment, Observer {
                        onStoredMatchesChanged(it)
                    })
                }
            }

            MyUiStates.LastPage -> {
                loading.visibility = View.GONE

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE

                activity?.snackBar(state.message, contentHome)
            }
            MyUiStates.NoConnection -> {
                //show no connect view
                loading.visibility = View.GONE

            }
            MyUiStates.Empty -> {
                //show Empty view
                loading.visibility = View.GONE

            }
            null -> {
            }
        }
    }

    private fun onStoredMatchesChanged(matchesList: List<MatchModel>) {
        setUpMatches(matchesList)
    }

    private fun animateView(image: View) {

        val rotateAnimation = AnimationUtils.loadAnimation(activity, com.koraextra.app.R.anim.rotate)
        image.startAnimation(rotateAnimation)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.koraextra.app.R.id.nav_notification -> {
                findNavController().navigate(com.koraextra.app.R.id.notificationsFragment)
            }
            com.koraextra.app.R.id.nav_favourites -> {
                findNavController().navigate(com.koraextra.app.R.id.favoritesFragment)
            }
            com.koraextra.app.R.id.nav_newsPaper -> {
                findNavController().navigate(com.koraextra.app.R.id.latestNewsFragment)
            }
            com.koraextra.app.R.id.nav_champions -> {
                findNavController().navigate(
                    com.koraextra.app.R.id.tournamentsFragment
                )
            }
            com.koraextra.app.R.id.nav_TopScorer -> {
                findNavController().navigate(com.koraextra.app.R.id.topScorersFragment)
            }
            com.koraextra.app.R.id.nav_settings -> {
                findNavController().navigate(com.koraextra.app.R.id.settingsFragment)
            }
            com.koraextra.app.R.id.nav_login -> {
                findNavController().navigate(com.koraextra.app.R.id.loginFragment)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpMatches(matchesList: List<MatchModel>) {

        this.matchesList.clear()
        this.matchesList.addAll(matchesList)
        activity?.toast("${matchesList.size}")

        adapterMatches.notifyDataSetChanged()
        adapterMatches.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    com.koraextra.app.R.id.matchItem -> {
                        findNavController().navigate(com.koraextra.app.R.id.matchFragment)
                    }
                }
            }
        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)
    }


}
