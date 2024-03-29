package com.fictivestudios.orb_icons.helper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.fictivestudios.orb_icons.activities.MainActivity
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.databinding.Titlebarbinding

class Titlebar : RelativeLayout {
    var binding: Titlebarbinding? = null

    constructor(context: Context) : super(context) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout(context)
    }

    fun initLayout(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.titlebar, this, true)
    }

    fun setTitle(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = VISIBLE
        binding?.tvTitleWhite?.visibility = GONE

        binding?.ivBack?.visibility = View.GONE
        binding?.ivMenu?.visibility = View.VISIBLE
        binding?.ivBack2?.visibility = View.GONE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.tvTitleRed?.text = title
        binding?.tvTitleWhite?.text = title

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivBack?.setOnClickListener(OnClickListener {
            getActivityContext.onBackPressed()
        })
    }

    fun setTitleSecond(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = GONE
        binding?.tvTitleWhite?.visibility = VISIBLE

        binding?.ivBack?.visibility = View.VISIBLE
        binding?.ivMenu?.visibility = View.GONE
        binding?.ivBack2?.visibility = View.GONE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.tvTitleRed?.text = title
        binding?.tvTitleWhite?.text = title

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivBack?.setOnClickListener(OnClickListener {
            getActivityContext.onBackPressed()
        })
    }

    fun setTitleThird(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = GONE
        binding?.tvTitleWhite?.visibility = VISIBLE

        binding?.ivBack?.visibility = View.GONE
        binding?.ivMenu?.visibility = View.GONE
        binding?.ivBack2?.visibility = View.GONE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.tvTitleWhite?.text = title
    }

    fun setTitleForth(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = VISIBLE
        binding?.tvTitleWhite?.visibility = GONE

        binding?.ivBack?.visibility = View.GONE
        binding?.ivMenu?.visibility = View.VISIBLE
        binding?.ivBack2?.visibility = View.VISIBLE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.tvTitleRed?.text = title
        binding?.tvTitleWhite?.text = title

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivBack2?.setOnClickListener(OnClickListener {
            getActivityContext.onBackPressed()
        })
    }

    fun hideTitleBar() {
        resetTitlebar()
    }

    fun resetTitlebar() {
        binding?.rlTitlebarMainLayout?.setVisibility(View.GONE)
    }

    fun setHideTitle() {
        resetTitlebar()
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.ivBack?.visibility = View.GONE
    }
}