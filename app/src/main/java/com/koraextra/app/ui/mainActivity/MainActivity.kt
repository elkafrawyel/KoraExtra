package com.koraextra.app.ui.mainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.koraextra.app.R
import com.koraextra.app.utily.Constants
import com.koraextra.app.utily.changeLanguage
import com.koraextra.app.utily.saveLanguage
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.koraextra.app.data.models.auth.SocialBody
import com.koraextra.app.utily.*

class MainActivity : AppCompatActivity(), GraphRequest.GraphJSONObjectCallback {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MainViewModel

    var callbackManager: CallbackManager? = null
    private var faceBookAccessToken: AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.uiState.observeEvent(this, { onFaceBookLoginResponse(it) })
    }

    private fun onFaceBookLoginResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
            }
            MyUiStates.Success -> {
                toast(getString(R.string.loginSuccess))
                findNavController(R.id.fragment).navigate(R.id.homeFragment)
            }
            MyUiStates.LastPage -> {
            }
            is MyUiStates.Error -> {
                snackBar(states.message, rootView)
            }
            MyUiStates.NoConnection -> {
            }
            MyUiStates.Empty -> {

            }
        }
    }

    private fun loginWithFaceBookData(body: SocialBody) {
        viewModel.socialLogin(body = body)


        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this);

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
                    toast("Facebook login failed")
                }
            }

            override fun onCancel() {
                toast("Facebook login Cancelled")
            }

            override fun onError(exception: FacebookException) {
                toast("Facebook login failed$exception")
            }
        })

    }

    fun loginWithFacebook() {
        login_button.performClick()
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

            if (Injector.getPreferenceHelper().fireBaseToken != null) {
                val body =
                    SocialBody(
                        name = firstName.toString(), email = email.toString(),
                        api_token_rule = "facebook", api_token = "",
                        firebasetoken = Injector.getPreferenceHelper().fireBaseToken!!
                    )
                loginWithFaceBookData(body)

            } else {
                toast(getString(R.string.tryAgainLater))
            }
        } catch (ex: Exception) {

        }
    }

    override fun onResume() {
        super.onResume()
        saveLanguage(Constants.Language.ARABIC)
        changeLanguage()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 64206) {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }
}
