package com.technado.orbicons.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.LoginFragmentBinding
import com.technado.orbicons.databinding.SignupFragmentBinding
import com.technado.orbicons.helper.Titlebar
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : BaseFragment() {
    var binding: SignupFragmentBinding? = null
    var showHide: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        binding?.signinLayout?.setOnClickListener(View.OnClickListener {
            getActivityContext?.onBackPressed()
        })

        getActivityContext!!.lockMenu()

        binding?.imageLayout?.setOnClickListener(View.OnClickListener {

        })

        binding?.btnSignup?.setOnClickListener(View.OnClickListener {
            val fullName = binding?.edtFullName?.text.toString().trim()
            val email = binding?.edtEmail?.text.toString().trim()
            val password = binding?.edtPassword?.text.toString().trim()
            val confirmPassword = binding?.edtConfirmPassword?.text.toString().trim()

            if (fullName.isEmpty()) {
                binding?.edtFullName?.setError("Full Name Required")
                binding?.edtFullName?.requestFocus()
            } else if (email.isEmpty()) {
                binding?.edtEmail?.setError("Email Required")
                binding?.edtEmail?.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(
                    email
                ).matches()
            ) {
                binding?.edtEmail?.setError("Email Pattern Required")
                binding?.edtEmail?.requestFocus()
            } else if (password.isEmpty()) {
                binding?.edtPassword?.setError("Password Required")
                binding?.edtPassword?.requestFocus()
            } else if (password.length <= 5) {
                binding?.edtPassword?.setError("Password Length")
                binding?.edtPassword?.requestFocus()
            } else if (confirmPassword.isEmpty()) {
                binding?.edtConfirmPassword?.setError("Confirm Password Required")
                binding?.edtConfirmPassword?.requestFocus()
            } else if (!password.equals(confirmPassword)) {
                binding?.edtConfirmPassword?.setError("Passwords Not Matched")
                binding?.edtConfirmPassword?.requestFocus()
            } else {
                Toast.makeText(getActivityContext, "" + fullName, Toast.LENGTH_SHORT).show()
                getActivityContext?.replaceFragment(
                    VerificationFragment("SIGNUP"),
                    VerificationFragment::class.java.simpleName,
                    true,
                    true
                )
            }
        })

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }
}