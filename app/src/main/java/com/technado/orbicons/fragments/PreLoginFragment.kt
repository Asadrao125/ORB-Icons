package com.technado.orbicons.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.LoginFragmentBinding
import com.technado.orbicons.databinding.PreLoginFragmentBinding
import com.technado.orbicons.helper.Titlebar

class PreLoginFragment : BaseFragment() {
    var binding: PreLoginFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pre_login, container, false)

        binding?.loginWithGoogle?.setOnClickListener(View.OnClickListener {
            getActivityContext?.replaceFragment(
                HomeFragment(),
                HomeFragment::class.java.simpleName,
                true,
                true
            )
        })

        binding?.loginWithFacebook?.setOnClickListener(View.OnClickListener {
            getActivityContext?.replaceFragment(
                HomeFragment(),
                HomeFragment::class.java.simpleName,
                true,
                true
            )
        })

        binding?.loginWithEmail?.setOnClickListener(View.OnClickListener {
            getActivityContext?.replaceFragment(
                LoginFragment(),
                LoginFragment::class.java.simpleName,
                true,
                true
            )
        })

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.resetTitlebar()
    }
}