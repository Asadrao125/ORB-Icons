package com.fictivestudios.demoapp.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.fictivestudios.orb_icons.activities.MainActivity
import com.fictivestudios.orb_icons.activities.RegistrationActivity
import com.fictivestudios.orb_icons.helper.Titlebar

abstract class BaseFragment : Fragment() {
    var getActivityContext: MainActivity? = null
    var getRegisterActivityContext: RegistrationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    abstract fun setTitlebar(titlebar: Titlebar)

    fun getActivityContext(): MainActivity? {
        return getActivityContext
    }

    fun getRegisterActivityContext(): RegistrationActivity? {
        return getRegisterActivityContext
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            val contex = context as MainActivity?
            if (contex != null)
                getActivityContext = context
        } else if (context is RegistrationActivity) {
            val contex = context as RegistrationActivity?
            if (contex != null)
                getRegisterActivityContext = context
        }
    }

    override fun onResume() {
        super.onResume()
        if (getActivityContext != null) {
            setTitlebar(getActivityContext!!.getTitlebar())
        }
    }
}