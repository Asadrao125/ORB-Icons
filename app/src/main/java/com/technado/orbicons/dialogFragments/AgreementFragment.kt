package com.technado.orbicons.dialogFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.technado.orbicons.R
import com.technado.orbicons.databinding.AgreementDialogFragmentBinding
import com.technado.orbicons.databinding.ExitDialogFragmentBinding

class AgreementFragment : DialogFragment() {
    var binding: AgreementDialogFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_agreement, container, false)

        return binding?.root
    }
}