package com.koraextra.app.ui.mainActivity.auth.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.data.models.auth.LoginBody
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.backImage

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.uiState.observeEvent(this, { onLoginResponse(it) })

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        loginMbtn.setOnClickListener {
            login()
        }

        singUpMbtnLogin.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        forgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPassFragment)
        }
    }

    private fun onLoginResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                findNavController().navigate(R.id.homeFragment)

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
                    login()
                }
            }
            MyUiStates.Empty -> {

            }
        }

    }

    private fun login() {
        if (email.text.isBlank()) {
            email.error = getString(R.string.empty_email)
            return
        }

        if (password.text.isBlank()) {
            password.error = getString(R.string.empty_password)
            return
        }

        if (Injector.getPreferenceHelper().fireBaseToken != null) {
            val body = LoginBody(
                email = email.text.toString(),
                password = password.text.toString(),
                firebasetoken = Injector.getPreferenceHelper().fireBaseToken
            )
            viewModel.login(body = body)

        } else {
            activity?.snackBar(getString(R.string.tryAgainLater), rootView)
        }
    }

}
