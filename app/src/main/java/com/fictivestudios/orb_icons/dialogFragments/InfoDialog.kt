package com.fictivestudios.demoapp.dialogFragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.databinding.InformationDialogFragmentBinding

class InfoDialog(var infoText: String) : DialogFragment() {
    var binding: InformationDialogFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_dialog, container, false)

        getDialog()?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding?.tvInfo?.text = infoText
        binding?.cvClose?.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })

        return binding?.root
    }
}