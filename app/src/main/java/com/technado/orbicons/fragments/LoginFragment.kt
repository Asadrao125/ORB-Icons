package com.technado.orbicons.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.LoginFragmentBinding
import com.technado.orbicons.dialogFragments.AgreementFragment
import com.technado.orbicons.helper.Titlebar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    var binding: LoginFragmentBinding? = null
    lateinit var cbPrivacyPolicy: CheckBox
    lateinit var cbTermsConditins: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        agreementDialog()

        binding?.btnLogin?.setOnClickListener(View.OnClickListener {

            val email = binding?.edtEmail?.text.toString().trim()
            val password = binding?.edtPassword?.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail.setError("Email Required")
                edtEmail.requestFocus()
            } else if (password.isEmpty()) {
                edtPassword.setError("Password Required")
                edtPassword.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(
                    email
                ).matches()
            ) {
                edtEmail.setError("Email Pattern Required")
                edtEmail.requestFocus()
            } else if (password.length <= 5) {
                edtPassword.setError("Password Length")
                edtPassword.requestFocus()
            } else {
                if (cbPrivacyPolicy.isChecked && cbTermsConditins.isChecked) {
                    getActivityContext?.clearBackStack()
                    getActivityContext?.replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        true,
                        true
                    )
                } else {
                    cbPrivacyPolicy.setError("")
                    cbTermsConditins.setError("")
                    Toast.makeText(
                        getActivityContext,
                        "Please accept agreement",
                        Toast.LENGTH_SHORT
                    ).show()
                    agreementDialog()
                }
            }
        })

        binding?.signupLayout?.setOnClickListener(View.OnClickListener {
            getActivityContext?.replaceFragment(
                SignupFragment(),
                SignupFragment::class.java.simpleName,
                true,
                true
            )
        })

        binding?.tvForgetPassword?.setOnClickListener(View.OnClickListener {
            getActivityContext?.replaceFragment(
                ForgetPasswordFragment(),
                ForgetPasswordFragment::class.java.simpleName,
                true,
                true
            )
        })

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Login")
    }

    private fun agreementDialog() {
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
            dialog.dismiss()
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}