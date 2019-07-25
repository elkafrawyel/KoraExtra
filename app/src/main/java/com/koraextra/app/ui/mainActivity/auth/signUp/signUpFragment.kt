package com.koraextra.app.ui.mainActivity.auth.signUp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.backImage

class signUpFragment : Fragment() {

    companion object {
        fun newInstance() = signUpFragment()
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
        backImage.setOnClickListener {
            findNavController().navigateUp()
        }

        singUpMbtn.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        loginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

    }

}
