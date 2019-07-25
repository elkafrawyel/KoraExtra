package com.koraextra.app.ui.mainActivity.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.backImage

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private val adapterFavourites= AdapterFavourites()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)


        backImage.setOnClickListener {
            findNavController().navigateUp()
        }
        val list = ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")

        adapterFavourites.replaceData(list)

        favouritesRv.adapter = adapterFavourites
        favouritesRv.setHasFixedSize(true)

    }

}
