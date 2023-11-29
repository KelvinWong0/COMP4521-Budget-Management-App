package com.example.compose


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CategoryDatabase
import com.example.compose.data.CategoryRepository
import com.example.compose.data.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {


    private val readAllData: LiveData<List<Category>>
    private val repository : CategoryRepository

    init {
        val categoryDao = CategoryDatabase.getInstance(application).categoryDao()
        repository = CategoryRepository(categoryDao)
        readAllData = repository.readAllData
    }



    fun  addCategory (category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

}