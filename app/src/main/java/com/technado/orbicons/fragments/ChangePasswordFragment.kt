package com.technado.orbicons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.ChangePasswordFragmentBinding
import com.technado.orbicons.databinding.FrogetPasswordFragmentBinding
import com.technado.orbicons.helper.Titlebar

class ChangePasswordFragment : BaseFragment() {
    var binding: ChangePasswordFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)

        binding?.btnChangePassword?.setOnClickListener(View.OnClickListener {
            val currentPassword = binding?.edtCurrentPassword?.text.toString().trim()
            val newPassword = binding?.edtCurrentPassword?.text.toString().trim()
            val confirmPassword = binding?.edtCurrentPassword?.text.toString().trim()

            if (currentPassword.isEmpty()) {
                binding?.edtCurrentPassword?.setError("Existing Password Required")
                binding?.edtCurrentPassword?.requestFocus()
            } else if (newPassword.isEmpty()) {
                binding?.edtNewPassword?.setError("Existing Password Required")
                binding?.edtNewPassword?.requestFocus()
            } else if (confirmPassword.isEmpty()) {
                binding?.edtConfirmPassword?.setError("Existing Password Required")
                binding?.edtConfirmPassword?.requestFocus()
            } else if (newPassword.equals(confirmPassword)) {
                binding?.edtConfirmPassword?.setError("Passwords Donot Match")
                binding?.edtConfirmPassword?.requestFocus()
            } else {
                Toast.makeText(getActivityContext, "All Set", Toast.LENGTH_SHORT).show()
            }
        })

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }
}