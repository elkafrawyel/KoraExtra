package com.koraextra.app.ui.mainActivity.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.MyUiStates
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private fun preferencesHelper() = Injector.getPreferenceHelper()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        viewModel.uiState.observe(this, Observer<MyUiStates?> { ofSettingResponse(it) })
        notiMatch.isChecked = preferencesHelper().notiMatch == 1
        notiSound.isChecked = preferencesHelper().notiSound == 1
        notiStatus.isChecked = preferencesHelper().notiStatus == 1
        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        notiMatch.setOnClickListener {
            if (notiMatch.isChecked) {
                notiMatch.isChecked = true
                preferencesHelper().notiMatch = 1
                viewModel.setting("notiMatch", 1)
            } else {
                notiMatch.isChecked = false
                preferencesHelper().notiMatch = 0
                viewModel.setting("notiMatch", 0)

            }
        }

        notiSound.setOnClickListener {
            if (notiSound.isChecked) {
                notiSound.isChecked = true
                preferencesHelper().notiSound = 1
                viewModel.setting("notiSound", 1)
            } else {
                notiSound.isChecked = false
                preferencesHelper().notiSound = 0
                viewModel.setting("notiSound", 0)

            }
        }

        notiStatus.setOnClickListener {
            if (notiStatus.isChecked) {
                notiStatus.isChecked = true
                preferencesHelper().notiStatus = 1
                viewModel.setting("noti", 1)
            } else {
                notiStatus.isChecked = false
                preferencesHelper().notiStatus = 0
                viewModel.setting("noti", 0)

            }
        }

    }

    private fun ofSettingResponse(it: MyUiStates?) {

        when (it) {
            MyUiStates.Loading -> {
            }
            MyUiStates.Success -> {
                activity?.toast(viewModel.message)
            }
            MyUiStates.LastPage -> {
            }
            is MyUiStates.Error -> {
                activity?.toast(it.message)
            }
            MyUiStates.NoConnection -> {
            }
            MyUiStates.Empty -> {
            }
            null -> {
            }
        }
    }

}
