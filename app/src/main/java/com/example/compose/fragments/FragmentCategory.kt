package com.example.compose.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record

class FragmentCategory : Fragment(R.layout.fragment_category) {
    private lateinit var switchOnOff: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvSwitchYes: android.widget.TextView
    private lateinit var tvSwitchNo: android.widget.TextView
    private lateinit var dataViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        val btn    = view.findViewById<ActionMenuItemView>(R.id.fl_category_action)

        btn.setOnClickListener{
            tempDatabaseInsert()
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
        dataViewModel.addCategory(category)
        Toast.makeText(requireContext(),"successful!", Toast.LENGTH_LONG).show()
    }

    private fun tempDatabaseInsert(){
        val record1 = Record(0, "Record1", 1, "200", "INCOME", "30-11-2023")
        val record2 = Record(0, "Record2", 1, "-20", "EXPENSE", "30-11-2023")
        val record3 = Record(0, "Record3", 1, "-60", "EXPENSE", "30-11-2023")
        val record4 = Record(0, "Record4", 1, "150", "INCOME", "30-11-2023")
        dataViewModel.addRecord(record1)
        dataViewModel.addRecord(record2)
        dataViewModel.addRecord(record3)
        dataViewModel.addRecord(record4)
        Toast.makeText(requireContext(),"records added successful!", Toast.LENGTH_LONG).show()
    }
}