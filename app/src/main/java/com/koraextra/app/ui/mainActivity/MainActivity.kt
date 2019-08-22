package com.koraextra.app.ui.mainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.koraextra.app.R
import com.koraextra.app.utily.Constants
import com.koraextra.app.utily.changeLanguage
import com.koraextra.app.utily.saveLanguage
import com.koraextra.app.utily.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), GraphRequest.GraphJSONObjectCallback {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    var callbackManager: CallbackManager? = null
    private var faceBookAccessToken: AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(getApplicationContext());
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
                Log.e("medo",exception.message.toString())
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
            toast("Done")

//            login("$firstName $lastName", email.toString(), imageUrl, faceBookAccessToken!!.token, "facebook")

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
        if (requestCode == 64206){
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }
}
