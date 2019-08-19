package com.koraextra.app.ui.mainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.tournament.TournamentFragmentDirections
import com.koraextra.app.utily.Constants
import com.koraextra.app.utily.changeLanguage
import com.koraextra.app.utily.saveLanguage

class MainActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        saveLanguage(Constants.Language.ARABIC)
        changeLanguage()

    }
}
