package com.example.compose

import androidx.fragment.app.activityViewModels
import com.example.compose.CategoryViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

class FragmentCategory : Fragment(R.layout.fragment_category) {
    private lateinit var switchOnOff: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvSwitchYes: android.widget.TextView
    private lateinit var tvSwitchNo: android.widget.TextView
    private val categoryViewModel: CategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    tvSwitchYes.setTextColor(Color.BLACK)
                    tvSwitchNo.setTextColor(Color.BLACK)
                }
                else -> {
                    tvSwitchYes.setTextColor(Color.BLACK)
                    tvSwitchNo.setTextColor(Color.BLACK)
                }
            }
        }
    }
}