package com.koraextra.app.ui.mainActivity.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.koraextra.app.utily.MyUiStates
import com.google.android.material.navigation.NavigationView
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.ui.splashActivity.SplashActivity
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    SwipeRefreshLayout.OnRefreshListener,
    Observer<List<MatchModel>> {

    override fun onChanged(it: List<MatchModel>?) {
        onStoredMatchesChanged(it!!)
    }


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private val matchesList: ArrayList<MatchModel> = arrayListOf()
    private val adapterMatches = AdapterMatches(matchesList).also {
        it.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter
                                                                                  , view, position ->

            viewModel.isSwitchOn = liveSwitch.isChecked
            val match = (adapter.data[position] as MatchModel)
            when (view?.id) {
                R.id.matchItem -> {
                    matchId = match.fixtureId!!
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.")
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToMatchFragment(matchId)
                        findNavController().navigate(action)
                    }
                }

                R.id.homeImg,
                R.id.homeName -> {
                    mainViewModel.setTeamId(match.homeTeamid!!)
                    mainViewModel.setTeamName(match.homeTeam?.teamName!!)
                    mainViewModel.setTeamLogo(match.homeTeam.logo!!)
                    mainViewModel.setTeamFavo(match.homeTeam.favorite!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)
                }

                R.id.awayImg,
                R.id.awayName -> {
                    mainViewModel.setTeamId(match.awayTeamid!!)
                    mainViewModel.setTeamName(match.awayTeam?.teamName!!)
                    mainViewModel.setTeamLogo(match.awayTeam.logo!!)
                    mainViewModel.setTeamFavo(match.awayTeam.favorite!!)
                    mainViewModel.setLeagueId(match.leagueId!!)

                    findNavController().navigate(R.id.teamFragment)

                }
            }
        }
    }
    private var matchId = 0

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
            findNavController().navigate(R.id.fuckFragment)

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

        setAuthState()
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

        val socialItemId = 7
        val viewClicked = navigationView.menu.getItem(socialItemId).actionView
        viewClicked.findViewById<LinearLayout>(R.id.facebookView).setOnClickListener {
            //            activity?.toast("Facebook")
            startActivity(getOpenFacebookIntent())
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
//        animateView(homeFragmentAppName)

        homeSwipe.setOnRefreshListener(this)

        homeSwipe.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        matchesRv.adapter = adapterMatches
        matchesRv.setHasFixedSize(true)


//        adView.loadAd(
//            AdRequest.Builder()
//                .addTestDevice("410E806C439261CF851B922E62D371EB")
//                .build()
//        )

        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = "ca-app-pub-7642057802414977/1115862358"
        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("5392457EFAD98BBB3676457D618EBB83").build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("5392457EFAD98BBB3676457D618EBB83").build())

                // Code to be executed when the interstitial ad is closed.
                val action =
                    HomeFragmentDirections.actionHomeFragmentToMatchFragment(matchId)
                findNavController().navigate(action)
            }
        }
    }

    private fun setAuthState() {
        val preferencesHelper = Injector.getPreferenceHelper()
        if (preferencesHelper.isLoggedIn) {
            navigationView.menu.getItem(6).title = context?.resources?.getString(R.string.logOut)
        } else {
            navigationView.menu.getItem(6).title = context?.resources?.getString(R.string.login)
        }
    }

    private fun getTodayMatches() {
        //start with current date
        val date = activity?.getCurrentDate()
        viewModel.date = date
        dayName_tv.text = activity?.getDayName(date!!)
        date_tv.text = activity?.getDateStringFromString(date!!)
        // get normal matches with above date
        clearObservers()
        viewModel.getMatchesList()
    }

    override fun onRefresh() {
        clearObservers()
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
        clearObservers()
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
                val date = year.toString() + "-" + String.format(
                    Locale("en"),
                    "%02d-%02d",
                    (monthOfYear + 1),
                    dayOfMonth
                )
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
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    homeRootView
                ) {
                    clearObservers()
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
        if(matchesList.isNotEmpty()) {
            setUpMatches(matchesList)
        }else{
            onEmpty()
        }
    }

    private fun animateView(image: View) {
        val rotateAnimation = AnimationUtils.loadAnimation(activity, R.anim.rotate)
        image.startAnimation(rotateAnimation)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val preferencesHelper = Injector.getPreferenceHelper()
        when (item.itemId) {
//            R.id.nav_notification -> {
//                if (preferencesHelper.isLoggedIn) {
//                    findNavController().navigate(R.id.notificationsFragment)
//                } else {
//                    activity?.snackBarWithAction(
//                        getString(R.string.you_must_login),
//                        getString(R.string.login),
//                        homeRootView
//                    ) {
//                        findNavController().navigate(R.id.loginFragment)
//                    }
//                }
//            }
            R.id.nav_favourites -> {
                if (preferencesHelper.isLoggedIn) {
                    findNavController().navigate(R.id.favoritesFragment)
                } else {
                    activity?.snackBarWithAction(
                        getString(R.string.you_must_login),
                        getString(R.string.login),
                        homeRootView
                    ) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
//            R.id.nav_newsPaper -> {
//
//                findNavController().navigate(R.id.latestNewsFragment)
//            }
            R.id.nav_champions -> {
                findNavController().navigate(
                    R.id.tournamentsFragment
                )
            }
            R.id.nav_TopScorer -> {
                findNavController().navigate(R.id.topScorersFragment)
            }
            R.id.nav_settings -> {
                if (preferencesHelper.isLoggedIn) {
                    findNavController().navigate(R.id.settingsFragment)
                } else {
                    activity?.snackBarWithAction(
                        getString(R.string.you_must_login),
                        getString(R.string.login),
                        homeRootView
                    ) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
            R.id.nav_shearApp -> {
                val appPackageName: String? = context?.getPackageName()
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)
                        )
                    )
                } catch (e: java.lang.Exception) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                        )
                    );
                }
            }
            R.id.nav_login -> {
                if (preferencesHelper.isLoggedIn) {
                    preferencesHelper.clear()
                    navigationView.menu.getItem(7).title =
                        context?.resources?.getString(R.string.login)

                    SplashActivity.start(context!!)
                    activity?.finish()
                } else {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
        viewModel.isSwitchOn = liveSwitch.isChecked
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpMatches(list: List<MatchModel>) {
        matchesList.clear()
        activity?.addAddsToArray(list)?.let { matchesList.addAll(it) }
        adapterMatches.notifyDataSetChanged()
        matchesRv.visibility = View.VISIBLE
        loading.visibility = View.GONE
        emptyMessageTv.visibility = View.GONE
    }

    private fun getOpenFacebookIntent(): Intent {

        try {
            context?.packageManager!!.getPackageInfo("com.facebook.katana", 0);
            return Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/2487675394583898"));
        } catch (e: Exception) {
            return Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/KoooraGoalLive/")
            );
        }
    }

}
