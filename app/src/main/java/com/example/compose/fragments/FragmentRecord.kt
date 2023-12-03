package com.example.compose.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.fragments.list.ListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FragmentRecord: Fragment(R.layout.fragment_record){
    private lateinit var dataViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvRecord = view.findViewById<RecyclerView>(R.id.recordList)
        val adapter = ListAdapter()
        rvRecord.adapter = adapter
        rvRecord.layoutManager = LinearLayoutManager(requireContext())

        val toptoolbar = view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

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

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.readAllRecord.observe(viewLifecycleOwner, Observer{records ->
            adapter.setData(records.sortedBy { it.date }.reversed())
        })
    }
}