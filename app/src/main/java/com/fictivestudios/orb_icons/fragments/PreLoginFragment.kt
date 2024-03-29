package com.fictivestudios.orb_icons.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.databinding.PreLoginFragmentBinding
import com.fictivestudios.orb_icons.helper.SharedPref
import com.fictivestudios.orb_icons.helper.Titlebar

class PreLoginFragment : BaseFragment() {
    var binding: PreLoginFragmentBinding? = null
    lateinit var cbPrivacyPolicy: CheckBox
    lateinit var cbTermsConditins: CheckBox
    lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pre_login, container, false)

        sharedPref = SharedPref(getActivityContext!!)

        binding?.loginWithGoogle?.setOnClickListener(View.OnClickListener {
            agreementDialog(1)
        })

        binding?.loginWithFacebook?.setOnClickListener(View.OnClickListener {
            agreementDialog(1)
        })

        binding?.loginWithEmail?.setOnClickListener(View.OnClickListener {
            agreementDialog(0)
        })

        getActivityContext!!.lockMenu()

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleThird(getActivityContext!!, "Pre Login")
    }

    private fun agreementDialog(i: Int) {
        val dialog = Dialog(getActivityContext!!)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_agreement)
        cbTermsConditins = dialog.findViewById(R.id.cbTermsConditins) as CheckBox
        cbPrivacyPolicy = dialog.findViewById(R.id.cbPrivacyPolicy) as CheckBox
        val tvAccept = dialog.findViewById(R.id.tvAccept) as TextView
        val tvDecline = dialog.findViewById(R.id.tvDecline) as TextView
        val imgCross = dialog.findViewById(R.id.imgCross) as ImageView

        tvDecline.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        imgCross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        tvAccept.setOnClickListener(View.OnClickListener {
            if (cbPrivacyPolicy.isChecked && cbTermsConditins.isChecked) {
                if (i == 1) {

                    sharedPref.write("login", "true")

                    getActivityContext!!.clearBackStack()
                    getActivityContext?.replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        true,
                        true
                    )
                    dialog.dismiss()
                } else if (i == 0) {
                    getActivityContext?.replaceFragment(
                        LoginFragment(),
                        LoginFragment::class.java.simpleName,
                        true,
                        true
                    )
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(
                    getActivityContext!!,
                    "Terms and Conditions required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}