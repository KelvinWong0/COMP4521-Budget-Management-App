package com.example.compose.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.lifecycle.Observer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.GridAdapter
import com.example.compose.fragments.list.ListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FragmentCategory : Fragment(R.layout.fragment_category) {
    private lateinit var switchOnOff: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvSwitchYes: android.widget.TextView
    private lateinit var tvSwitchNo: android.widget.TextView
    private lateinit var dataViewModel: DataViewModel
    private lateinit var adapter : GridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        val btn    = view.findViewById<ActionMenuItemView>(R.id.fl_category_action)
        val toptoolbar = view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

        val gvCategory = view.findViewById<GridView>(R.id.gvCategory)
        adapter = GridAdapter()
        gvCategory.adapter = adapter

        btn.setOnClickListener{
            insertDataToDatabase()
        }
        dataViewModel.readCategoryByType(false).observe(viewLifecycleOwner, Observer{categories ->

            adapter.setData(categories)
        })

        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    Toast.makeText(activity?.applicationContext, "Income selected", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(activity?.applicationContext, "Expense selected", Toast.LENGTH_SHORT).show()
                }

            }
            dataViewModel.readCategoryByType(checked).observe(viewLifecycleOwner, Observer{categories ->

                adapter.setData(categories)
            })
        }



        toptoolbar.setNavigationOnClickListener{
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Warning")
                .setMessage("Delete all record?")
                .setNeutralButton("Cancel") { dialog, which ->
                    // Respond to neutral button press

                }
//                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                    // Respond to negative button press
//                }
                .setPositiveButton("Proceed") { dialog, which ->
                    // Respond to positive button press
                    dataViewModel.clearRecordTable()
                    Toast.makeText(activity?.applicationContext, "All record deleted!", Toast.LENGTH_LONG).show()
                }
                .show()
        }



    }



    private fun insertDataToDatabase(){
        val category = Category(0, "clothings", R.drawable.ic_cat_entertainment, false)
        dataViewModel.addCategory(category)
        Toast.makeText(requireContext(),"successful!", Toast.LENGTH_LONG).show()
    }

    private fun tempDatabaseInsert(){
        val record1 = Record(0, "Record1", R.drawable.ic_cat_savings, "200", "30-11-2023","INCOME" )
        val record2 = Record(0, "Record2", R.drawable.ic_cat_entertainment, "-20", "30-11-2023","EXPENSE")
        val record3 = Record(0, "Record3", R.drawable.ic_cat_shopping, "-60", "30-11-2023","EXPENSE" )
        val record4 = Record(0, "Record4", R.drawable.ic_cat_savings, "150", "30-11-2023","INCOME" )
        dataViewModel.addRecord(record1)
        dataViewModel.addRecord(record2)
        dataViewModel.addRecord(record3)
        dataViewModel.addRecord(record4)
        Toast.makeText(requireContext(),"records added successful!", Toast.LENGTH_LONG).show()
    }
}