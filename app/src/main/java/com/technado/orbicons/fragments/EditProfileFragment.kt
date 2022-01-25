package com.technado.orbicons.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.DownloadsFragmentBinding
import com.technado.orbicons.databinding.EditProfileFragmentBinding
import com.technado.orbicons.helper.Titlebar

class EditProfileFragment : BaseFragment() {
    var binding: EditProfileFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        getActivityContext!!.lockMenu()

        binding?.imgUpload?.setOnClickListener(View.OnClickListener {

        })

        binding?.btnDone?.setOnClickListener(View.OnClickListener {
            val fullName = binding?.edtFullName?.text.toString().trim()
            if (fullName.isEmpty()) {
                binding?.edtFullName?.setError("Full Name Required")
                binding?.edtFullName?.requestFocus()
            } else {
                getActivityContext!!.onBackPressed()
            }
        })

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleSecond(getActivityContext!!, "Edit Profile")
    }
}