package com.koraextra.app.ui.splashActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.NetworkUtils
import com.google.firebase.iid.FirebaseInstanceId
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.utily.Constants
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.changeLanguage
import com.koraextra.app.utily.saveLanguage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync

class SplashActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                GlobalScope.launch {
                    val token = task.result?.token!!
                    Injector.saveFirebaseToken().saveToken(token)
                }
            }
        }

        saveLanguage(Constants.Language.ARABIC)
        changeLanguage()

        doAsync {
            Injector.getAppDatabase().myDao().deleteMatches()
        }

        MainActivity.start(this)
        finish()
    }
}
