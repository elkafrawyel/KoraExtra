package com.koraextra.app.ui.mainActivity.auth.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.koraextra.app.R
import com.koraextra.app.data.models.auth.LoginBody
import com.koraextra.app.data.models.auth.SocialBody
import com.koraextra.app.ui.mainActivity.MainActivity
import com.koraextra.app.ui.mainActivity.MainViewModel
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.backImage
import org.json.JSONObject
import java.lang.Exception

class LoginFragment : Fragment() {


    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var mainViewModel: MainViewModel
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        viewModel.uiState.observeEvent(this, { onLoginResponse(it) })
        mainViewModel.uiState.observeEvent(this, { onLoginGoogleResponse(it) })
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

        facebookMbtn.setOnClickListener {
            (activity as MainActivity).loginWithFacebook()
        }
        //================================ Google ======================================

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)

        googleMbtn.setOnClickListener {
            val signInIntent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, 100)
        }

    }

    private fun onLoginGoogleResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                activity?.toast(getString(R.string.loginSuccess))


                findNavController().navigate(
                    R.id.homeFragment, null, NavOptions.Builder().setPopUpTo(
                        R.id.loginFragment,
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
                    login()
                }
            }
            MyUiStates.Empty -> {

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                if (Injector.getPreferenceHelper().fireBaseToken != null) {
                    val body =
                        SocialBody(
                            name = account.displayName.toString(), email = account.email.toString(),
                            api_token_rule = "google", api_token = account.idToken!!,
                            firebasetoken = Injector.getPreferenceHelper().fireBaseToken!!
                        )
                    loginWithGoogleData(body)
                } else {
                    activity?.toast(getString(R.string.tryAgainLater))
                }
            } else
                activity?.toast("Error happened")
        } catch (e: ApiException) {
            Log.e("Medo", e.message.toString())
            activity?.toast("Google SignIn Error")
        }
    }

    private fun loginWithGoogleData(socialBody: SocialBody) {
        mainViewModel.socialLogin(socialBody)
    }

    private fun onLoginResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
                loading.visibility = View.VISIBLE
            }
            MyUiStates.Success -> {
                loading.visibility = View.GONE
                activity?.toast(getString(R.string.loginSuccess))

                findNavController().navigate(
                    R.id.homeFragment, null, NavOptions.Builder().setPopUpTo(
                        R.id.loginFragment,
                        false
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
                firebasetoken = Injector.getPreferenceHelper().fireBaseToken!!
            )
            viewModel.login(body = body)

        } else {
            activity?.snackBar(getString(R.string.tryAgainLater), rootView)
        }
    }

}
