package com.example.compose

import com.example.compose.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.lifecycle.ViewModelProvider
import com.example.compose.data.models.Category
import com.example.compose.fragments.list.ivGridAdapter
import com.google.android.material.textfield.TextInputEditText


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

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_cat_activity)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.addCatTopBar)
        val btn    = toolbar.findViewById<ActionMenuItemView>(R.id.addCat)
        val tvCatName = findViewById<TextInputEditText>(R.id.catName)
        val acCatType = findViewById<AutoCompleteTextView>(R.id.catType)

        toolbar.setNavigationOnClickListener{
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }

        btn.setOnClickListener{
            val name:String = tvCatName.text.toString()
            val type:String = acCatType.text.toString()
            val iconId = adapter.getSelectedImageId()
            tvCatName.error =  null
            acCatType.error = null

            if(name != "" && type != "" && iconId != null){
                setResult(Activity.RESULT_OK, intent)
                dataViewModel.addCategory(Category(0,  name, iconId, type == "Income"))
                finish()
            }else{
                if(name == ""){
                    tvCatName.error = "fill category name"
                }
                if(type == ""){
                    acCatType.error = "select category type"
                }
                if(iconId == null){
                    Toast.makeText(this, "Please select an icon for cateogry", Toast.LENGTH_LONG).show()
                }

            }
//            setResult(Activity.RESULT_OK, intent)
//            dataViewModel.addCategory(Category())
//            finish()
        }

        val gvIcon = findViewById<GridView>(R.id.gvIcon)
        adapter = ivGridAdapter()
        adapter.setData(images)
        gvIcon.adapter = adapter


    }
}