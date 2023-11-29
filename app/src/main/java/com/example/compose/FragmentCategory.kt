package com.example.compose

import androidx.fragment.app.activityViewModels
import com.example.compose.CategoryViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.data.models.Category

class FragmentCategory : Fragment(R.layout.fragment_category) {
    private lateinit var switchOnOff: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvSwitchYes: android.widget.TextView
    private lateinit var tvSwitchNo: android.widget.TextView
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        val btn    = view.findViewById<ActionMenuItemView>(R.id.fl_category_action)

        btn.setOnClickListener{
            insertDataToDatabase()
        }

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

    private fun insertDataToDatabase(){
        val category = Category(0, "clothings", "@drawables/NO_CREATED", "EXPENSE")
        categoryViewModel.addCategory(category)
        Toast.makeText(requireContext(),"successful!", Toast.LENGTH_LONG).show()
    }
}