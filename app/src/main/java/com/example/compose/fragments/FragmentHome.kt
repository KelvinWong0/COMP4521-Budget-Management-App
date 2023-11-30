package com.example.compose.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.compose.DataViewModel
import com.example.compose.EditRecordActivity
import com.example.compose.R
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
    }
}