package com.example.compose.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.compose.R

class FragmentSettings : Fragment(R.layout.fragment_settings){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sbtnNoti = view.findViewById<Switch>(R.id.switchNoti)
        sbtnNoti.setOnCheckedChangeListener { _, isChecked ->
            when{
                isChecked -> {
                    if(ContextCompat.checkSelfPermission( view.context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){

                    }
                }
                else -> {

                }
            }
        }
    }
}