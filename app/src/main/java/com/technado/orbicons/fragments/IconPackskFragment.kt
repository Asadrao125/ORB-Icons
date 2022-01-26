package com.technado.orbicons.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.adapter.IconPackAdapter
import com.technado.orbicons.databinding.ChangePasswordFragmentBinding
import com.technado.orbicons.databinding.IconPacksFragmentBinding
import com.technado.orbicons.helper.Titlebar

class IconPackskFragment : BaseFragment() {
    var binding: IconPacksFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_icon_packsk, container, false)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(getActivityContext)
        binding?.recyclerView?.setHasFixedSize(true)

        binding?.recyclerView?.adapter = IconPackAdapter(
            getActivityContext!!,
            getActivityContext!!.resources.getStringArray(R.array.dummy)
                .toList() as ArrayList<String>
        )

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Icon Packs")
    }

}