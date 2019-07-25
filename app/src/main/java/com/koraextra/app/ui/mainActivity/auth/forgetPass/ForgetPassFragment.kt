package com.koraextra.app.ui.mainActivity.auth.forgetPass

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.koraextra.app.R
import kotlinx.android.synthetic.main.forget_pass_fragment.*

class ForgetPassFragment : Fragment() {

    companion object {
        fun newInstance() = ForgetPassFragment()
    }

    private lateinit var viewModel: ForgetPassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forget_pass_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgetPassViewModel::class.java)
        // TODO: Use the ViewModel

        setEmail.setOnClickListener {
            findNavController().navigate(R.id.resetPasswordFragment)
        }
    }

}
