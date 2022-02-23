package com.fictivestudios.orb_icons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mukesh.OnOtpCompletionListener
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.databinding.VerificationFragmentBinding

class VerificationFragment(val signupORforgetpassword: String) : BaseFragment() {
    var binding: VerificationFragmentBinding? = null
    var code: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verification, container, false)

        getActivityContext!!.lockMenu()

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
        titlebar.setTitleSecond(getActivityContext!!, "Verification")
    }
}