package com.example.compose.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.EditRecordActivity
import com.example.compose.R
import com.example.compose.fragments.list.ListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentRecord: Fragment(R.layout.fragment_record){
    private lateinit var dataViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvRecord = view.findViewById<RecyclerView>(R.id.recordList)
        val adapter = ListAdapter()
        rvRecord.adapter = adapter
        rvRecord.layoutManager = LinearLayoutManager(requireContext())

        val header = view.findViewById<ConstraintLayout>(R.id.header_record)
        val toptoolbar = header.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

        toptoolbar.setNavigationOnClickListener{
            
        }

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.readAllRecord.observe(viewLifecycleOwner, Observer{records ->
            adapter.setData(records)
        })
    }
}