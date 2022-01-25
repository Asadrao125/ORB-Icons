package com.technado.orbicons.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.ChangePasswordFragmentBinding
import com.technado.orbicons.databinding.ResetPasswordFragmentBinding
import com.technado.orbicons.helper.Titlebar

class ResetPasswordFragment : BaseFragment() {
    var binding: ResetPasswordFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)

        getActivityContext!!.lockMenu()

        binding?.btnReset?.setOnClickListener(View.OnClickListener {
            val newPassword = binding?.edtNewPassword?.text.toString().trim()
            val confirmPassword = binding?.edtConfirmPassword?.text.toString().trim()

            if (newPassword.isEmpty()) {
                binding?.edtNewPassword?.setError("New Password Required")
                binding?.edtNewPassword?.requestFocus()
            } else if (newPassword.length <= 5) {
                binding?.edtNewPassword?.setError("Password Length")
                binding?.edtNewPassword?.requestFocus()
            } else if (confirmPassword.isEmpty()) {
                binding?.edtConfirmPassword?.setError("Confirm Password Required")
                binding?.edtConfirmPassword?.requestFocus()
            } else if (confirmPassword.length <= 5) {
                binding?.edtConfirmPassword?.setError("Password Length")
                binding?.edtConfirmPassword?.requestFocus()
            } else if (confirmPassword.equals(newPassword)) {
                binding?.edtConfirmPassword?.setError("Passwords Donot Match")
                binding?.edtConfirmPassword?.requestFocus()
            } else {
                getActivityContext!!.clearBackStack()
                getActivityContext!!.replaceFragment(
                    LoginFragment(),
                    LoginFragment::class.java.simpleName,
                    false,
                    false
                )
            }
        })

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleSecond(getActivityContext!!, "Reset Password")
    }
}