package com.technado.orbicons.activities

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.technado.demoapp.base.BaseActivity
import com.technado.demoapp.dialogFragments.ExitDialog
import com.technado.orbicons.R
import com.technado.orbicons.databinding.MainActivityBinding
import com.technado.orbicons.fragments.*
import com.technado.orbicons.helper.Titlebar

class MainActivity : BaseActivity(), View.OnClickListener {
    var binding: MainActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setMainFrameLayoutID()
        setListener()
        replaceFragment(PreLoginFragment(), PreLoginFragment::class.java.simpleName, true, false)
    }

    fun setListener() {
        binding?.llHome?.setOnClickListener(this)
        binding?.llDownloads?.setOnClickListener(this)
        binding?.llTersAndConditions?.setOnClickListener(this)
        binding?.llPrivacyPolicy?.setOnClickListener(this)
        binding?.llChangePasword?.setOnClickListener(this)
        binding?.llLogout?.setOnClickListener(this)
    }

    fun getTitlebar(): Titlebar {
        return binding!!.titlebar
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            val fragmentManager = supportFragmentManager
            val fragments: List<Fragment> = fragmentManager.fragments
            val last: Fragment = fragments.get(fragments.size - 1)

            supportFragmentManager.popBackStack()

        } else {
            //val exitDialog = ExitDialog()
            //exitDialog.show(supportFragmentManager, "exitDialog")
        }
    }

    fun mainHideTitle() {
        binding!!.titlebar.visibility = View.GONE
    }

    fun mainShowTitle() {
        binding!!.titlebar.visibility = View.VISIBLE
    }

    fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    override fun setMainFrameLayoutID() {
        mainFrameLayoutID = binding?.mainContainer!!.id
    }

    fun unlockMenu() {
        binding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun lockMenu() {
        binding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun openDrawer() {
        binding!!.drawerlayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawers() {
        binding!!.drawerlayout.closeDrawers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llHome -> {
                replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName, false, true)
                closeDrawers()
            }

            R.id.llDownloads -> {
                replaceFragment(
                    DownloadsFragment(),
                    DownloadsFragment::class.java.simpleName,
                    false,
                    true
                )
                closeDrawers()
            }

            R.id.llTersAndConditions -> {
                replaceFragment(
                    TermsAndConditionsFragment(),
                    TermsAndConditionsFragment::class.java.simpleName,
                    false,
                    true
                )
                closeDrawers()
            }

            R.id.llPrivacyPolicy -> {
                replaceFragment(
                    PrivacyPolicyFragment(),
                    PrivacyPolicyFragment::class.java.simpleName,
                    false,
                    true
                )
                closeDrawers()
            }

            R.id.llChangePasword -> {
                replaceFragment(
                    ChangePasswordFragment(),
                    ChangePasswordFragment::class.java.simpleName,
                    false,
                    true
                )
                closeDrawers()
            }

            R.id.llLogout -> {
                closeDrawers()
            }
        }
    }
}