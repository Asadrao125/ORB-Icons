package com.fictivestudios.orb_icons.dialogFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.databinding.AgreementDialogFragmentBinding

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