package com.koraextra.app.ui.mainActivity.home

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.koraextra.app.utily.MyUiStates
import com.google.android.material.navigation.NavigationView
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener,
    Observer<List<MatchModel>> {

    override fun onChanged(it: List<MatchModel>?) {
        onStoredMatchesChanged(it!!)
    }


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private val matchesList: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(matchesList).also {
        it.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter
                                                                                  , view, position ->

            viewModel.isSwitchOn = liveSwitch.isChecked
            val match = (adapter.data as List<MatchModel>)[position]
            when (view?.id) {
                R.id.matchItem -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToMatchFragment(match.fixtureId!!)
                    findNavController().navigate(action)
                }

                R.id.homeImg,
                R.id.homeName -> {
                    mainViewModel.setTeamId(match.homeTeam?.teamId!!)
                    mainViewModel.setTeamName(match.homeTeam.teamName!!)
                    mainViewModel.setTeamLogo(match.homeTeam.logo!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)
                }

                R.id.awayImg,
                R.id.awayName -> {
                    mainViewModel.setTeamId(match.awayTeam?.teamId!!)
                    mainViewModel.setTeamName(match.awayTeam.teamName!!)
                    mainViewModel.setTeamLogo(match.awayTeam.logo!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)

                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        if (!viewModel.opened) {
            viewModel.opened = true
            clearObservers()
            getTodayMatches()
        } else {

            dayName_tv.text = activity?.getDayName(viewModel.date!!)
            date_tv.text = activity?.getDateStringFromString(viewModel.date!!)
            clearObservers()
            viewModel.storedMatchesLiveData?.observe(this@HomeFragment, this)

            if (viewModel.isSwitchOn)
                linearHeader.visibility = View.GONE
            else
                linearHeader.visibility = View.VISIBLE


        }

        navigationView.setNavigationItemSelectedListener(this)

        drawerToggleImgBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        viewModel.uiState.observe(this, Observer<MyUiStates?> { onMatchesChanged(it) })

        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                activity!!,
                drawerLayout, R.string.open, R.string.close
            ) {
                private val scaleFactor = 6f
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val slideX = (drawerView.width * slideOffset * -1)
                    homeRootView.translationX = slideX
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
        liveSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (viewModel.isSwitchOn) {
                viewModel.isSwitchOn = false

            } else {

                clearObservers()
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

        }

        date_tv.setOnClickListener {
            openDatePicker()
        }
        dayName_tv.setOnClickListener {
            openDatePicker()
        }

        nextImg.setOnClickListener {
            changeDay(true)
        }

        previousImg.setOnClickListener {
            changeDay()
        }
        animateView(homeFragmentAppName)

        homeSwipe.setOnRefreshListener(this)

        homeSwipe.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)
    }

    private fun getTodayMatches() {
        //start with current date
        val date = activity?.getCurrentDate()
        viewModel.date = date
        dayName_tv.text = activity?.getDayName(date!!)
        date_tv.text = activity?.getDateStringFromString(date!!)
        // get normal matches with above date
        viewModel.getMatchesList()
    }

    override fun onRefresh() {
        viewModel.getMatchesList(liveSwitch.isChecked)
        homeSwipe.isRefreshing = false

    }

    private fun changeDay(next: Boolean = false) {
        val date = activity?.getDateFromString(viewModel.date!!)
        val c = Calendar.getInstance()
        c.time = date
        if (next) {
            c.add(Calendar.DATE, 1)
        } else {
            c.add(Calendar.DATE, -1)
        }
        val timeFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val dateString = timeFormat.format(c.time)
        viewModel.date = dateString
        dayName_tv.text = activity?.getDayName(dateString)
        date_tv.text = activity?.getDateStringFromString(dateString)
//        clearObservers()
        viewModel.getMatchesList()
    }

    private fun clearObservers() {
        if (viewModel.storedMatchesLiveData != null)
            viewModel.storedMatchesLiveData?.removeObserver(this)
    }

    @SuppressLint("PrivateResource")
    private fun openDatePicker() {
        val date = activity?.getDateFromString(viewModel.date!!)
        val c = Calendar.getInstance()
        c.time = date
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            context!!,
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Picker_Date_Calendar,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val date = year.toString() + "-" + String.format("%02d-%02d", (monthOfYear + 1), dayOfMonth)
//                activity?.toast(date)


                viewModel.date = date
                clearObservers()
                viewModel.getMatchesList()

                dayName_tv.text = activity?.getDayName(date)
                date_tv.text = activity?.getDateStringFromString(date)

            },
            year,
            month,
            day
        )

        datePicker.show()
    }

    private fun onMatchesChanged(state: MyUiStates?) {
        when (state) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE
//                clearObservers()
                viewModel.storedMatchesLiveData?.observe(this@HomeFragment, this)
            }

            MyUiStates.LastPage -> {
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                activity?.snackBar(state.message, homeRootView)
            }

            MyUiStates.NoConnection -> {
                //show no connect view
                loading.visibility = View.GONE
                matchesRv.visibility = View.GONE
                emptyMessageTv.visibility = View.GONE

                activity?.snackBarWithAction(
                    context?.resources?.getString(R.string.noConnectionMessage),
                    homeRootView
                ) {
                    viewModel.getMatchesList(liveSwitch.isChecked)
                }
            }
            MyUiStates.Empty -> {
                onEmpty()
            }
            null -> {
            }
        }
    }

    private fun onEmpty() {
        //show Empty view
        emptyMessageTv.visibility = View.VISIBLE
        loading.visibility = View.GONE
        matchesRv.visibility = View.GONE

    }

    private fun onStoredMatchesChanged(matchesList: List<MatchModel>) {
        setUpMatches(matchesList)
    }

    private fun animateView(image: View) {
        val rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.rotate)
        image.startAnimation(rotateAnimation)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notification -> {
                findNavController().navigate(R.id.notificationsFragment)
            }
            R.id.nav_favourites -> {
                findNavController().navigate(R.id.favoritesFragment)
            }
            R.id.nav_newsPaper -> {
                findNavController().navigate(R.id.latestNewsFragment)
            }
            R.id.nav_champions -> {
                findNavController().navigate(
                    R.id.tournamentsFragment
                )
            }
            R.id.nav_TopScorer -> {
                findNavController().navigate(R.id.topScorersFragment)
            }
            R.id.nav_settings -> {
                findNavController().navigate(R.id.settingsFragment)
            }
            R.id.nav_login -> {
                findNavController().navigate(R.id.loginFragment)
            }
        }
        viewModel.isSwitchOn = liveSwitch.isChecked
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpMatches(list: List<MatchModel>) {
        matchesList.clear()
        matchesList.addAll(list)
        adapterMatches.notifyDataSetChanged()
        matchesRv.visibility = View.VISIBLE
    }

}
