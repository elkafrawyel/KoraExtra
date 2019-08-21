package com.koraextra.app.ui.mainActivity.auth.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.backImage
import org.json.JSONObject
import java.lang.Exception

class LoginFragment : Fragment(), GraphRequest.GraphJSONObjectCallback {


    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private var mGoogleSignInClient: GoogleSignInClient? = null
    var callbackManager: CallbackManager? = null
    private var faceBookAccessToken: AccessToken? = null

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

        //============================= FaceBook ========================================
        callbackManager = CallbackManager.Factory.create()
        login_button.setPermissions(listOf("email", "public_profile"))
        LoginManager.getInstance().logOut()

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (isLoggedIn) {
            LoginManager.getInstance().logOut()
        }

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
                if (isLoggedIn) {
                    loadUserProfile(accessToken)
                } else {
                    activity?.toast("Facebook login failed")
                }
            }

            override fun onCancel() {
                activity?.toast("Facebook login Cancelled")
            }

            override fun onError(exception: FacebookException) {
                activity?.toast("Facebook login failed")
            }
        })

        facebookMbtn.setOnClickListener {
            login_button.performClick()
        }

    }

    private fun loadUserProfile(accessToken: AccessToken?) {
        val graphRequest = GraphRequest.newMeRequest(accessToken, this)
        faceBookAccessToken = accessToken
        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
        try {
            val firstName = `object`!!.get("first_name")
            val lastName = `object`.get("last_name")
            val email = `object`.get("email")
            val id = `object`.get("id")
            val imageUrl = "https://graph.facebook.com/$id/picture?type=normal"
            activity?.toast("Done")

//            login("$firstName $lastName", email.toString(), imageUrl, faceBookAccessToken!!.token, "facebook")

        } catch (ex: Exception) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }else {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null)
                activity?.toast(account.displayName!!)
            else
                activity?.toast("Error happened")
        } catch (e: ApiException) {
            activity?.toast("Google SignIn Error")
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
                firebasetoken = Injector.getPreferenceHelper().fireBaseToken!!
            )
            viewModel.login(body = body)

        } else {
            activity?.snackBar(getString(R.string.tryAgainLater), rootView)
        }
    }

}
