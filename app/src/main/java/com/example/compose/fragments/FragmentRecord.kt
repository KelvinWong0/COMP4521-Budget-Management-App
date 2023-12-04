package com.example.compose.fragments

import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.EditRecordActivity
import com.example.compose.R
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.ListAdapter
import com.example.compose.fragments.list.ListAdapterSp
import com.example.compose.fragments.list.SimpleGridAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Date


class FragmentRecord: Fragment(R.layout.fragment_record){
    private lateinit var dataViewModel: DataViewModel
    private lateinit var recordList: List<Record>
    private var recordDeleteMode: Boolean = false

//    private lateinit var records  : Map<Date, List<Record>>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val rvRecord = view.findViewById<RecyclerView>(R.id.recordList)
        var adapter = ListAdapterSp(dataViewModel, recordDeleteMode)
        rvRecord.adapter = adapter
        rvRecord.layoutManager = LinearLayoutManager(requireContext())

        val toptoolbar = view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

        toptoolbar.setNavigationOnClickListener{
            recordDeleteMode = !recordDeleteMode
            if(recordDeleteMode){
                toptoolbar.setNavigationIcon(R.drawable.ic_view)
            }else{
                toptoolbar.setNavigationIcon(R.drawable.ic_delete_24)
            }
            adapter = ListAdapterSp(dataViewModel,  recordDeleteMode)
            rvRecord.adapter = adapter
            adapter.setData(recordList)
        }

        val  btn = toptoolbar.findViewById<ActionMenuItemView>(R.id.fl_record_action)

        btn.setOnClickListener{
            Intent(requireContext(),  EditRecordActivity::class.java).also {
                startActivity(it)
            }
        }





        dataViewModel.readAllRecord.observe(viewLifecycleOwner, Observer { records ->
            recordList = records.sortedBy { it.date }.reversed()
            adapter.setData(recordList)
        })
    }
}