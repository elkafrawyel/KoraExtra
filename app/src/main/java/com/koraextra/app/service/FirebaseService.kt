package com.koraextra.app.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.koraextra.app.utily.Injector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        GlobalScope.launch {
            Injector.getMatchesRepo().getMatches("fixtures/live")
        }
    }
}