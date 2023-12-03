package com.example.compose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddCatActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_cat_activity)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.header_add_cat)
        toolbar.setNavigationOnClickListener{

        }

    }
}