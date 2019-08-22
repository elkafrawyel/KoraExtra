package com.koraextra.app.ui.mainActivity.auth.resetPassword

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import com.koraextra.app.utily.*
import kotlinx.android.synthetic.main.reset_password_fragment.*

class ResetPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reset_password_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)

    }




}
