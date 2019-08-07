package com.koraextra.app.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.ui.KoraViewModel

class MainViewModel : KoraViewModel() {

    private var _match = MutableLiveData<MatchModel>()
    val matchLiveData: LiveData<MatchModel>
        get() = _match

    fun setMatch(match: MatchModel) {
        _match.value = match
    }

}