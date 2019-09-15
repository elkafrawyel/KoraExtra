package com.koraextra.app.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.getCurrentDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("ahmedT","ahmedahmed")
        GlobalScope.launch {
            Injector.getMatchesRepo().getMatches("fixtures/date/"+baseContext.getCurrentDate())
        }
    }
}