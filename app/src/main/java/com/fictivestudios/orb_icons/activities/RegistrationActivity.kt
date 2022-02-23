package com.fictivestudios.orb_icons.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.fictivestudios.demoapp.base.BaseActivity
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.fragments.HomeFragment
import com.fictivestudios.orb_icons.databinding.RegisterActivityBinding

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