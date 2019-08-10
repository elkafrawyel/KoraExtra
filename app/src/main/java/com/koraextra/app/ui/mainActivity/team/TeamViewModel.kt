package com.koraextra.app.ui.mainActivity.team

import androidx.lifecycle.ViewModel;

class TeamViewModel : ViewModel() {
    var id:Int?=null
    var name:String?=null
    var logo:String?=null


    var opened: Boolean = false
    var selectedTab: Int = 0
    var selectedFromTabs: Boolean = true
}
