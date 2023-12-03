package com.example.compose.fragments

import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.SimpleGridAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.sql.Date


class FragmentCategory : Fragment(R.layout.fragment_category) {
    private lateinit var switchOnOff: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvSwitchYes: android.widget.TextView
    private lateinit var tvSwitchNo: android.widget.TextView
    private lateinit var dataViewModel: DataViewModel
    private lateinit var adapter : SimpleGridAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        val btn    = view.findViewById<ActionMenuItemView>(R.id.fl_category_action)
        val toptoolbar = view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

        val gvCategory = view.findViewById<GridView>(R.id.gvCategory)
        adapter = SimpleGridAdapter()
        gvCategory.adapter = adapter

        btn.setOnClickListener{
            tempDatabaseInsert()
        }
        dataViewModel.readCategoryByType(false).observe(viewLifecycleOwner, Observer{categories ->
            adapter.setData(categories)
        })

        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {


                }
                else -> {

                }
            }
            adapter.clearSelected()
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

//        gvCategory.onItemClickListener = OnItemClickListener { parent, view, position, id -> //here you can use the position to determine what checkbox to check
//            //this assumes that you have an array of your checkboxes as well. called checkbox
//            val grid = view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.gridCat)
//            grid.get(position).setChecked(!checkBox.isChecked())
//        };
    }



    private fun insertDataToDatabase(){
        val category = Category(0,"clothings", R.drawable.ic_cat_entertainment, false)
        dataViewModel.addCategory(category)
        Toast.makeText(requireContext(),"successful!", Toast.LENGTH_LONG).show()
    }

    private fun tempDatabaseInsert(){
        val record1 = Record(0,  "GTA6"     , Category(0,"games", R.drawable.ic_cat_entertainment, false)   , "200", Date(30-11-2023))
//        val record2 = Record(0,  "underwear", Category(0,"clothings", R.drawable.ic_cat_shopping, false)    , "-20", "30-11-2023")
//        val record3 = Record(0,  "water"    , Category(0,"bill", R.drawable.ic_cat_bills, false)            , "-60", "30-11-2023")
//        val record4 = Record(0,  "milk"     , Category(0,"grocery", R.drawable.ic_cat_grocery, false)       , "150", "30-11-2023")
        dataViewModel.addRecord(record1)
//        dataViewModel.addRecord(record2)
//        dataViewModel.addRecord(record3)
//        dataViewModel.addRecord(record4)
        Toast.makeText(requireContext(),"records added successful!", Toast.LENGTH_LONG).show()
    }
}