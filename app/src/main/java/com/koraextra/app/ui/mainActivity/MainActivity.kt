package com.koraextra.app.ui.mainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.koraextra.app.R
import com.koraextra.app.ui.mainActivity.tournament.TournamentFragmentDirections

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

    fun openMatchFragment(id: Int) {
        val action =
            TournamentFragmentDirections.actionTournamentFragmentToMatchFragment(id)
        findNavController(R.id.fragment).navigate(action)
    }
}
