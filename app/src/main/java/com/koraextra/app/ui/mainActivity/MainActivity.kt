package com.koraextra.app.ui.mainActivity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.koraextra.app.R
import com.koraextra.app.data.models.auth.SocialBody
import com.koraextra.app.ui.splashActivity.SplashActivity
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*

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

    private fun onFaceBookLoginResponse(states: MyUiStates) {
        when (states) {
            MyUiStates.Loading -> {
            }
            MyUiStates.Success -> {
                toast(getString(R.string.loginSuccess))
//                findNavController(R.id.fragment).navigate(
//                    R.id.homeFragment, null, NavOptions.Builder().setPopUpTo(
//                        R.id.loginFragment,
//                        false
//                    ).build()
//                )
                SplashActivity.start(this)
                finish()
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
                        api_token_rule = "facebook", api_token = faceBookAccessToken!!.token,
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


//    @RequiresApi(Build.VERSION_CODES.O)
//    fun generateSSHKey(context: Context){
//        try {
//            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.getEncoder().encode(md.digest()))
//                Log.i("AppLog", "key:$hashKey")
//            }
//        } catch (e: Exception) {
//            Log.e("AppLog", "error:", e)
//        }
//
//    }
}
