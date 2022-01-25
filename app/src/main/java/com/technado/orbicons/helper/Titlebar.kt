package com.technado.orbicons.helper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.technado.orbicons.activities.MainActivity
import com.technado.orbicons.R
import com.technado.orbicons.databinding.Titlebarbinding

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

    fun hideTitleBar() {
        resetTitlebar()
    }

    fun resetTitlebar() {
        binding?.rlTitlebarMainLayout?.setVisibility(View.GONE)
    }

    fun setTitle(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = VISIBLE
        binding?.tvTitleWhite?.visibility = GONE

        binding?.ivBack?.visibility = View.GONE
        binding?.ivMenu?.visibility = View.VISIBLE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.rlTitlebarMainLayout?.background =
            ContextCompat.getDrawable(context, R.drawable.background2)

        binding?.tvTitleRed?.text = title
        binding?.tvTitleWhite?.text = title

        binding?.ivBack?.background = null

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivBack?.setOnClickListener(OnClickListener {
            getActivityContext.onBackPressed()
        })
    }

    fun setTitleTCPP(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = VISIBLE
        binding?.tvTitleWhite?.visibility = GONE

        binding?.ivBack?.visibility = View.VISIBLE
        binding?.ivMenu?.visibility = View.GONE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)

        binding?.rlTitlebarMainLayout?.background =
            ContextCompat.getDrawable(context, R.drawable.background2)

        binding?.tvTitleRed?.text = title
        binding?.ivBack?.background = ContextCompat.getDrawable(context, R.drawable.red_shape)

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
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.rlTitlebarMainLayout?.background =
            ContextCompat.getDrawable(context, R.drawable.background)

        binding?.tvTitleRed?.text = title
        binding?.tvTitleWhite?.text = title

        binding?.ivBack?.background = null

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivBack?.setOnClickListener(OnClickListener {
            getActivityContext.onBackPressed()
        })
    }

    fun setColor() {
        binding?.rlTitlebarMainLayout?.setBackgroundColor(resources.getColor(R.color.trans))
    }

    fun setColor2() {
        binding?.rlTitlebarMainLayout?.setBackgroundColor(resources.getColor(R.color.blue))
    }

    fun setTitleThird(getActivityContext: MainActivity, title: String) {
        setHideTitle()
        binding?.tvTitleRed?.visibility = GONE
        binding?.tvTitleWhite?.visibility = VISIBLE

        binding?.ivBack?.visibility = View.GONE
        binding?.ivMenu?.visibility = View.GONE
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.rlTitlebarMainLayout?.background =
            ContextCompat.getDrawable(context, R.drawable.background)

        binding?.ivBack?.background = null

        binding?.tvTitleWhite?.text = title
    }

    fun setHideTitle() {
        resetTitlebar()
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.ivBack?.visibility = View.GONE
    }
}