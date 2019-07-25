package com.koraextra.app.ui.mainActivity.auth.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.login_fragment.*

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

        backImage.setOnClickListener {
            findNavController().navigateUp()
        }
        loginMbtn.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        singUpMbtnLogin.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        forgetPass.setOnClickListener {
            findNavController().navigate(R.id.forgetPassFragment)
        }
    }

}