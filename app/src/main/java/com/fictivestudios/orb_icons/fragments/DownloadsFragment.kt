package com.fictivestudios.orb_icons.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.adapter.DownloadIconPackAdapter
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.databinding.DownloadsFragmentBinding
import com.fictivestudios.orb_icons.model.IconPacksModel

class DownloadsFragment : BaseFragment() {
    var binding: DownloadsFragmentBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false)

        val list: ArrayList<IconPacksModel> = ArrayList()
        list.add(IconPacksModel("Anything Goes Icons", "5.00$", 1, getActivityContext!!.getDrawable(R.drawable.pack1_thumb)!!))
        list.add(IconPacksModel("Apps Icons", "3.00$", 1, getActivityContext!!.getDrawable(R.drawable.pack2_thumb)!!))

        /*list.add(IconPacksModel("Google Icons", "8.00$", 1, getActivityContext!!.getDrawable(R.drawable.pack3_thumb)!!))
        list.add(IconPacksModel("Marble Icons", "6.00$", 1, getActivityContext!!.getDrawable(R.drawable.pack4_thumb)!!))
        list.add(IconPacksModel("Folder Icons", "10.00$", 1, getActivityContext!!.getDrawable(R.drawable.pack5_thumb)!!))*/

        binding?.recyclerView?.layoutManager = LinearLayoutManager(getActivityContext)
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = DownloadIconPackAdapter(getActivityContext!!, list)

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleForth(getActivityContext!!, "Downloads")
    }
}