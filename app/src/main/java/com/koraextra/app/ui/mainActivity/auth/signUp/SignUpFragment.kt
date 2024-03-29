package com.koraextra.app.ui.mainActivity.auth.signUp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.backImage

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        viewModel.uiState.observeEvent(this, { onRegisterResponse(it) })

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        singUpMbtn.setOnClickListener {
            register()
        }

        loginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

    }

    private fun register() {
        if (username.text.isBlank()) {
            username.error = getString(R.string.empty_user_name)
            return
        }

        if (email.text.isBlank()) {
            email.error = getString(R.string.empty_email)
            return
        }

        if (password.text.isBlank()) {
            password.error = getString(R.string.empty_password)
            return
        }

        if (Injector.getPreferenceHelper().fireBaseToken != null) {
            val body = RegisterBody(
                name = username.text.toString(),
                email = email.text.toString(),
                password = password.text.toString(),
                confirmPassword = password.text.toString(),
                firebasetoken = Injector.getPreferenceHelper().fireBaseToken!!
            )
            viewModel.register(body = body)
        } else {
            activity?.snackBar(getString(R.string.tryAgainLater), rootView)
        }
    }

    private fun onRegisterResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                findNavController().navigate(
                    R.id.homeFragment, null, NavOptions.Builder().setPopUpTo(
                        R.id.signUpFragment,
                        true
                    ).build()
                )
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
                    register()
                }
            }
            MyUiStates.Empty -> {

            }
        }
    }

}
