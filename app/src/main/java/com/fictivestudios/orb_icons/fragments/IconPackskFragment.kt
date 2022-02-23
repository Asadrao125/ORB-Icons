package com.fictivestudios.orb_icons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.adapter.IconPackAdapter
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.databinding.IconPacksFragmentBinding
import com.fictivestudios.orb_icons.model.AppModel
import com.fictivestudios.orb_icons.model.IconPacksModel

class IconPackskFragment : BaseFragment() {
    var binding: IconPacksFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_icon_packsk, container, false)

        val list: ArrayList<IconPacksModel> = ArrayList()
        list.add(IconPacksModel("Anything Goes Icons", "5.00$", 1))
        list.add(IconPacksModel("Apps Icons", "3.00$", 1))
        list.add(IconPacksModel("Google Icons", "8.00$", 1))
        list.add(IconPacksModel("Marble Icons", "6.00$", 1))
        list.add(IconPacksModel("Folder Icons", "10.00$", 1))

        binding?.recyclerView?.layoutManager = LinearLayoutManager(getActivityContext)
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = IconPackAdapter(getActivityContext!!, list)

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleForth(getActivityContext!!, "Icon Packs")
    }
}