package com.example.compose.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.compose.AddCatActivity
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
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var categoryList: List<Category>
    private var gridDeleteMode: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        switchOnOff = view.findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switchOnOff)
        switchOnOff.isChecked = false
        tvSwitchYes = view.findViewById<android.widget.TextView>(R.id.tvSwitchYes)
        tvSwitchNo = view.findViewById<android.widget.TextView>(R.id.tvSwitchNo)
        val btn    = view.findViewById<ActionMenuItemView>(R.id.fl_category_action)
        val toptoolbar = view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        val btnSort = view.findViewById<Switch>(R.id.btnSort)

        val gvCategory = view.findViewById<GridView>(R.id.gvCategory)
        adapter = SimpleGridAdapter(requireContext(),dataViewModel, gridDeleteMode)
        gvCategory.adapter = adapter

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result here
                val data: Intent? = result.data
                // ...
            }
        }

        btnSort.setOnCheckedChangeListener { _, isChecked ->
            when{
                isChecked -> {
                    adapter.setData(categoryList.sortedBy { it.name })
                }
                else -> {
                    adapter.setData(categoryList)
                }
            }
        }


        btn.setOnClickListener{
            Intent(requireContext(), AddCatActivity::class.java).also{
                launcher.launch(it)
            }
        }
        dataViewModel.readCategoryByType(false).observe(viewLifecycleOwner, Observer{categories ->
            categoryList = categories
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
                categoryList = categories
                adapter.setData(categories)
            })
        }





        toptoolbar.setNavigationOnClickListener{
            gridDeleteMode = !gridDeleteMode
            if(gridDeleteMode){
                toptoolbar.setNavigationIcon(R.drawable.ic_view)
            }else{
                toptoolbar.setNavigationIcon(R.drawable.ic_delete_24)
            }
            adapter = SimpleGridAdapter(requireContext(), dataViewModel, gridDeleteMode)
            gvCategory.adapter = adapter
            adapter.setData(categoryList)
        }



//        gvCategory.onItemClickListener = OnItemClickListener { parent, view, position, id -> //here you can use the position to determine what checkbox to check
//            //this assumes that you have an array of your checkboxes as well. called checkbox
//            val grid = view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.gridCat)
//            grid.get(position).setChecked(!checkBox.isChecked())
//        };
    }



//    private fun insertDataToDatabase(){
//        val category = Category(0,"clothings", R.drawable.ic_cat_entertainment, false)
//        dataViewModel.addCategory(category)
//        Toast.makeText(requireContext(),"successful!", Toast.LENGTH_LONG).show()
//    }
//
//    private fun tempDatabaseInsert(){
//        val record1 = Record(0,  "GTA6"     , Category(0,"games", R.drawable.ic_cat_entertainment, false)   , "200", Date(30-11-2023))
////        val record2 = Record(0,  "underwear", Category(0,"clothings", R.drawable.ic_cat_shopping, false)    , "-20", "30-11-2023")
////        val record3 = Record(0,  "water"    , Category(0,"bill", R.drawable.ic_cat_bills, false)            , "-60", "30-11-2023")
////        val record4 = Record(0,  "milk"     , Category(0,"grocery", R.drawable.ic_cat_grocery, false)       , "150", "30-11-2023")
//        dataViewModel.addRecord(record1)
////        dataViewModel.addRecord(record2)
////        dataViewModel.addRecord(record3)
////        dataViewModel.addRecord(record4)
//        Toast.makeText(requireContext(),"records added successful!", Toast.LENGTH_LONG).show()
//    }
}