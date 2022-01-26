package com.technado.orbicons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.technado.demoapp.base.BaseFragment
import com.technado.orbicons.R
import com.technado.orbicons.adapter.DownloadIconPackAdapter
import com.technado.orbicons.adapter.IconPackAdapter
import com.technado.orbicons.databinding.DownloadsFragmentBinding
import com.technado.orbicons.databinding.TCandPPPasswordFragmentBinding
import com.technado.orbicons.helper.Titlebar

class DownloadsFragment : BaseFragment() {
    var binding: DownloadsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(getActivityContext)
        binding?.recyclerView?.setHasFixedSize(true)

        binding?.recyclerView?.adapter = DownloadIconPackAdapter(
            getActivityContext!!,
            getActivityContext!!.resources.getStringArray(R.array.dummy)
                .toList() as ArrayList<String>
        )

        return binding!!.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Downloads")
    }
}