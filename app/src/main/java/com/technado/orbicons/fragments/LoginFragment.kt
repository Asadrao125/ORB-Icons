package com.technado.orbicons.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.LoginFragmentBinding
import com.technado.orbicons.helper.SharedPref
import com.technado.orbicons.helper.Titlebar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    var binding: LoginFragmentBinding? = null
    lateinit var edtPassword: TextInputEditText
    lateinit var edtEmail: TextInputEditText
    lateinit var sharedPref: SharedPref

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        getActivityContext!!.lockMenu()

        sharedPref = SharedPref(getActivityContext!!)

        edtPassword = binding?.edtPassword!!
        edtEmail = binding?.edtEmail!!

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
                sharedPref.write("login", "true")
                getActivityContext!!.clearBackStack()
                getActivityContext?.replaceFragment(
                    HomeFragment(),
                    HomeFragment::class.java.simpleName,
                    true,
                    true
                )
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
        titlebar.setTitleSecond(getActivityContext!!, "Login")
    }
}