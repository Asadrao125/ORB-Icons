package com.fictivestudios.orb_icons.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.databinding.FrogetPasswordFragmentBinding
import kotlinx.android.synthetic.main.fragment_login.*

class ForgetPasswordFragment : BaseFragment() {
    var binding: FrogetPasswordFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false)

        getActivityContext!!.lockMenu()

        binding?.btnSendCode?.setOnClickListener(View.OnClickListener {
            val email = binding?.edtEmail?.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail.setError("Email Required")
                edtEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(
                    email
                ).matches()
            ) {
                edtEmail.setError("Email Required")
                edtEmail.requestFocus()
            } else {
                Toast.makeText(getActivityContext, "" + email, Toast.LENGTH_SHORT).show()
                getActivityContext?.replaceFragment(
                    VerificationFragment("FORGET"),
                    VerificationFragment::class.java.simpleName,
                    true,
                    true
                )
            }
        })

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleSecond(getActivityContext!!, "Forget Password")
    }
}