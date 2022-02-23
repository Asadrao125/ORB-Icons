package com.fictivestudios.orb_icons.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fictivestudios.demoapp.base.BaseFragment
import com.fictivestudios.orb_icons.R
import com.fictivestudios.orb_icons.helper.Titlebar
import com.fictivestudios.orb_icons.databinding.EditProfileFragmentBinding
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : BaseFragment() {
    var binding: EditProfileFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        getActivityContext!!.lockMenu()

        binding?.imgUpload?.setOnClickListener(View.OnClickListener {
            galleryIntent()
        })

        binding?.imageProfile?.setOnClickListener(View.OnClickListener {
            galleryIntent()
        })

        binding?.btnDone?.setOnClickListener(View.OnClickListener {
            val fullName = binding?.edtFullName?.text.toString().trim()
            if (fullName.isEmpty()) {
                binding?.edtFullName?.setError("Full Name Required")
                binding?.edtFullName?.requestFocus()
            } else {
                getActivityContext!!.onBackPressed()
            }
        })

        return binding!!.root
    }

    fun convertZoneToDate(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH : mm : ss")
        val date: Date = inputFormat.parse("2022-01-26T01:03:30.000Z")
        val formattedDate: String = outputFormat.format(date)
        println(formattedDate)
        return formattedDate
    }

    fun galleryIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 124)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK && data != null && requestCode == 124) {
            try {
                val imageUri: Uri = data.data!!
                val imageStream: InputStream =
                    getActivityContext!!.getContentResolver().openInputStream(imageUri)!!
                val bitmap: Bitmap = BitmapFactory.decodeStream(imageStream)
                binding?.imageProfile?.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitleSecond(getActivityContext!!, "Edit Profile")
    }
}