package com.example.compose

import com.example.compose.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.compose.fragments.list.ivGridAdapter


class AddCatActivity: AppCompatActivity() {
    private lateinit var adapter : ivGridAdapter
    private val images =  intArrayOf(
        R.drawable.ic_cat_bills,
        R.drawable.ic_cat_entertainment,
        R.drawable.ic_cat_birthday,
        R.drawable.ic_cat_cafe,
        R.drawable.ic_cat_building,
        R.drawable.ic_cat_cigerate,
        R.drawable.ic_cat_cocktail,
        R.drawable.ic_cat_compose,
        R.drawable.ic_cat_construct,
        R.drawable.ic_cat_cookie,
        R.drawable.ic_cat_cutlery,
        R.drawable.ic_cat_dividend,
        R.drawable.ic_cat_fire,
        R.drawable.ic_cat_flight_and_hotel,
        R.drawable.ic_cat_gift,
        R.drawable.ic_cat_golf,
        R.drawable.ic_cat_grocery,
        R.drawable.ic_cat_heartbeat,
        R.drawable.ic_cat_hospitallity,
        R.drawable.ic_cat_housesale,
        R.drawable.ic_cat_icecream,
        R.drawable.ic_cat_investment,
        R.drawable.ic_cat_judge,
        R.drawable.ic_cat_light,
        R.drawable.ic_cat_medical,
        R.drawable.ic_cat_medicaldegree,
        R.drawable.ic_cat_oncology,
        R.drawable.ic_cat_pills,
        R.drawable.ic_cat_plant,
        R.drawable.ic_cat_quaso,
        R.drawable.ic_cat_ramen,
        R.drawable.ic_cat_restaurant,
        R.drawable.ic_cat_safeguard,
        R.drawable.ic_cat_saltwater,
        R.drawable.ic_cat_savings,
        R.drawable.ic_cat_salary,
        R.drawable.ic_cat_shopping,
        R.drawable.ic_cat_skillet,
        R.drawable.ic_cat_sleep,
        R.drawable.ic_cat_traffic,
        R.drawable.ic_cat_travel,
        R.drawable.ic_cat_vaccaine,
        R.drawable.ic_cat_workout
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_cat_activity)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.addCatTopBar)
        toolbar.setNavigationOnClickListener{
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        val gvIcon = findViewById<GridView>(R.id.gvIcon)
        adapter = ivGridAdapter()
        adapter.setData(images)
        gvIcon.adapter = adapter


    }
}