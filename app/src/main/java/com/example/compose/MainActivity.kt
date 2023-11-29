package com.example.compose


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)




        val fragmentRecord      = FragmentRecord()
        val fragmentCategory    = FragmentCategory()
        val fragmentHome        = FragmentHome()
        val fragmentReports     = FragmentReports()
        val fragmentSettings    = FragmentSettings()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.miHome;

        setCurrentFragment(fragmentHome)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.miRecord       -> setCurrentFragment(fragmentRecord)
                R.id.miCategory     -> setCurrentFragment(fragmentCategory)
                R.id.miHome         -> setCurrentFragment(fragmentHome)
                R.id.miReport       -> setCurrentFragment(fragmentReports)
                R.id.miSettings     -> setCurrentFragment(fragmentSettings)
            }
            true
        }


    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }


}
