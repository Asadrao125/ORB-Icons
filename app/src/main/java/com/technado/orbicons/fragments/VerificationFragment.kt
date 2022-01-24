package com.technado.orbicons.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mukesh.OnOtpCompletionListener
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.ChangePasswordFragmentBinding
import com.technado.orbicons.databinding.VerificationFragmentBinding
import com.technado.orbicons.helper.Titlebar

class VerificationFragment(val signupORforgetpassword: String) : BaseFragment() {
    var binding: VerificationFragmentBinding? = null
    var code: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verification, container, false)

        binding!!.otpView.setOtpCompletionListener(OnOtpCompletionListener {
            Toast.makeText(getActivityContext, "" + it, Toast.LENGTH_SHORT).show()
            code = it
        })

        binding?.btnVerify?.setOnClickListener(View.OnClickListener {
            if (code.length == 6) {
                if (signupORforgetpassword.equals("SIGNUP")) {
                    getActivityContext!!.clearBackStack()
                    getActivityContext?.replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        true,
                        true
                    )
                } else if (signupORforgetpassword.equals("FORGET")) {
                    getActivityContext!!.clearBackStack()
                    getActivityContext?.replaceFragment(
                        LoginFragment(),
                        LoginFragment::class.java.simpleName,
                        true,
                        true
                    )
                }
            } else {
                binding?.otpView?.setError("Length Must be 6")
                binding?.otpView?.requestFocus()
            }
        })

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }
}