package com.example.compose

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.compose.data.models.Record
import com.example.compose.fragments.list.ListAdapterII
import com.example.compose.fragments.list.ivGridAdapter

class CatRecordActivity : AppCompatActivity() {
    private lateinit var adapter : ivGridAdapter
    private lateinit var dataViewModel: DataViewModel
    private lateinit var recordsInCat : List<Record>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_in_cat)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val toptoolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.readCatTopBar)
        val lvRecordsInCat = findViewById<ListView>(R.id.lvRecordsInCat)

        toptoolbar.setNavigationOnClickListener{
            finish()
        }
        val categoryId: Int = intent.getIntExtra("categoryId", -1)
        if(categoryId  != -1){
            dataViewModel.readAllRecordInCategory(categoryId).observe(this, Observer { records ->
                recordsInCat = records
                val adapter = ListAdapterII(recordsInCat)
                lvRecordsInCat.adapter = adapter
            })
        }









    }
}