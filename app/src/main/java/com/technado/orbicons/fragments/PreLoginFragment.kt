package com.technado.orbicons.fragments

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
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.PreLoginFragmentBinding
import com.technado.orbicons.helper.Titlebar

class PreLoginFragment : BaseFragment() {
    var binding: PreLoginFragmentBinding? = null
    lateinit var cbPrivacyPolicy: CheckBox
    lateinit var cbTermsConditins: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pre_login, container, false)

        binding?.loginWithGoogle?.setOnClickListener(View.OnClickListener {
            agreementDialog(1)
        })

        binding?.loginWithFacebook?.setOnClickListener(View.OnClickListener {
            agreementDialog(1)
        })

        binding?.loginWithEmail?.setOnClickListener(View.OnClickListener {
            agreementDialog(0)
        })

        return binding?.root
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
            if (i == 1) {
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
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}