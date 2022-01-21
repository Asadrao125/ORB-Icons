package com.technado.orbicons.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseActivity
import com.technado.orbicons.R
import com.technado.orbicons.databinding.RegisterActivityBinding
import com.technado.orbicons.fragments.HomeFragment

class RegistrationActivity : BaseActivity() {
    var binding: RegisterActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        setMainFrameLayoutID()

        replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName, false, false)
    }

    override fun setMainFrameLayoutID() {
        mainFrameLayoutID = binding?.mainContainer!!.id
    }
}