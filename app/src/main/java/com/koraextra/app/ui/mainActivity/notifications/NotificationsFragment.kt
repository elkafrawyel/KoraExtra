package com.koraextra.app.ui.mainActivity.notifications

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.snackBar
import kotlinx.android.synthetic.main.notifications_fragment.*

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel
    private val adapterNotification = AdapterNotification()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notifications_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        viewModel.uiState.observe(this, Observer { onNotificationsResponse(it) })

        viewModel.getNotifiactions()
        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        notificationRv.adapter = adapterNotification
        notificationRv.setHasFixedSize(true)
    }

    private fun onNotificationsResponse(states: MyUiStates?) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                adapterNotification.replaceData(viewModel.notificationsList)
            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                activity?.snackBar(states.message, rootView)
            }
            MyUiStates.NoConnection -> {

            }
            MyUiStates.Empty -> {
                loading.visibility = View.GONE
                activity?.snackBar(getString(R.string.empry_notifications), rootView)
            }
            null -> {

            }
        }
    }

}
