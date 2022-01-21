package com.technado.orbicons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.helper.Titlebar

class TermsAndConditionsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false)
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Terms & Conditions")
    }
}