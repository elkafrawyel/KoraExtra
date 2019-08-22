package com.koraextra.app.ui.mainActivity.auth.forgetPass

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.data.models.auth.ResetPasswordBody
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.forget_pass_fragment.*

class ForgetPassFragment : Fragment() {

    companion object {
        fun newInstance() = ForgetPassFragment()
    }

    private lateinit var viewModel: ForgetPassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forget_pass_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgetPassViewModel::class.java)
        viewModel.uiState.observeEvent(this, { onResetResponse(it) })

        emailMbtn.setOnClickListener {
            resetPassword()
        }

    }

    private fun onResetResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                findNavController().navigate(R.id.homeFragment)
                activity?.toast(getString(R.string.email_sent_message))
            }
            MyUiStates.LastPage -> {
            }
            is MyUiStates.Error -> {
                loading.visibility = View.GONE
                activity?.snackBar(states.message, rootView)
            }
            MyUiStates.NoConnection -> {
                loading.visibility = View.GONE
                activity?.snackBarWithAction(
                    getString(R.string.noConnectionMessage),
                    getString(R.string.refresh),
                    rootView
                ) {
                    resetPassword()
                }
            }
            MyUiStates.Empty -> {

            }
        }
    }

    private fun resetPassword() {
        if (emailTv.text.isEmpty()) {
            emailTv.error = getString(R.string.empty_email)
            return
        }

        viewModel.resetPassword(ResetPasswordBody(email = emailTv.text.toString()))
    }

}
