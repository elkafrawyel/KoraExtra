package com.koraextra.app.ui.splashActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.utily.Constants
import com.koraextra.app.utily.changeLanguage
import com.koraextra.app.utily.saveLanguage


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

        saveLanguage(Constants.Language.ARABIC)
        changeLanguage()

        MainActivity.start(this)
        finish()
    }
}
