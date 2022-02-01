package com.technado.orbicons.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.technado.demoapp.base.BaseActivity
import com.technado.orbicons.R
import com.technado.orbicons.databinding.MainActivityBinding
import com.technado.orbicons.fragments.*
import com.technado.orbicons.helper.SharedPref
import com.technado.orbicons.helper.Titlebar
import java.io.FileNotFoundException
import java.io.InputStream

class MainActivity : BaseActivity(), View.OnClickListener {
    var binding: MainActivityBinding? = null
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setMainFrameLayoutID()
        setListener()

        sharedPref = SharedPref(this)

        binding?.imgClose?.setOnClickListener(View.OnClickListener {
            closeDrawers()
        })

        binding?.imgUpload?.setOnClickListener(View.OnClickListener {
            replaceFragment(
                EditProfileFragment(),
                EditProfileFragment::class.java.simpleName,
                true,
                false
            )
            closeDrawers()
        })

        if (sharedPref.read("login", "").equals("true")) {
            replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName, true, false)
        } else {
            replaceFragment(
                PreLoginFragment(),
                PreLoginFragment::class.java.simpleName,
                true,
                false
            )
        }
    }

    fun setListener() {
        binding?.llHome?.setOnClickListener(this)
        binding?.llDownloads?.setOnClickListener(this)
        binding?.llTersAndConditions?.setOnClickListener(this)
        binding?.llPrivacyPolicy?.setOnClickListener(this)
        binding?.llChangePasword?.setOnClickListener(this)
        binding?.llLogout?.setOnClickListener(this)
        binding?.llIconPacks?.setOnClickListener(this)
        binding?.imageViewProfile?.setOnClickListener(this)
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
            //finish()
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

            R.id.imageViewProfile -> {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 124)
            }

            R.id.llHome -> {
                replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName, false, true)
                closeDrawers()
            }

            R.id.llDownloads -> {
                replaceFragment(
                    DownloadsFragment(),
                    DownloadsFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }

            R.id.llIconPacks -> {
                replaceFragment(
                    IconPackskFragment(),
                    IconPackskFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }

            R.id.llTersAndConditions -> {
                replaceFragment(
                    TCandPPFragment("Terms & Conditions"),
                    TCandPPFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }

            R.id.llPrivacyPolicy -> {
                replaceFragment(
                    TCandPPFragment("Privacy Policy"),
                    TCandPPFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }

            R.id.llChangePasword -> {
                replaceFragment(
                    ChangePasswordFragment(),
                    ChangePasswordFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }

            R.id.llLogout -> {
                sharedPref.write("login", "false")
                clearBackStack()
                replaceFragment(
                    PreLoginFragment(),
                    PreLoginFragment::class.java.simpleName,
                    true,
                    true
                )
                closeDrawers()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null && requestCode == 124) {
            try {
                val imageUri: Uri = data.data!!
                val imageStream: InputStream = getContentResolver().openInputStream(imageUri)!!
                val bitmap: Bitmap = BitmapFactory.decodeStream(imageStream)
                binding?.imageViewProfile?.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}