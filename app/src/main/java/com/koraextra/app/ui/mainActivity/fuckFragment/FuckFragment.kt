package com.koraextra.app.ui.mainActivity.fuckFragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.utily.Injector

class FuckFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fuck, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val handler = Handler(Injector.getApplicationContext().mainLooper)
        val runnable = Runnable {
            findNavController().navigateUp()
        }
        handler.postDelayed(runnable, 300)

    }

}
