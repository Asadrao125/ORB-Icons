package com.technado.orbicons.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.ChangePasswordFragmentBinding
import com.technado.orbicons.databinding.TCandPPPasswordFragmentBinding
import com.technado.orbicons.helper.Titlebar

class TCandPPFragment(val title: String) : BaseFragment() {
    var binding: TCandPPPasswordFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_cand_p_p, container, false)

        getActivityContext!!.lockMenu()

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleForth(getActivityContext!!, title)
    }
}