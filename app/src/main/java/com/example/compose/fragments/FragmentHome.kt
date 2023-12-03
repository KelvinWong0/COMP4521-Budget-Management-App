package com.example.compose.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.EditRecordActivity
import com.example.compose.R
import com.example.compose.fragments.list.ListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentHome: Fragment(R.layout.fragment_home) {
    lateinit var dataViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabAdd = view.findViewById<FloatingActionButton>(R.id.add_fab)
        fabAdd.setOnClickListener{
            Intent(requireContext(), EditRecordActivity::class.java).also{
                startActivity(it)
            }
        }

        val rvRecord = view.findViewById<RecyclerView>(R.id.recordList)
        val adapter = ListAdapter()
        rvRecord.adapter = adapter
        rvRecord.layoutManager = LinearLayoutManager(requireContext())

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.readAllRecord.observe(viewLifecycleOwner, Observer{records ->
            adapter.setData(records)
        })
    }
}