package com.example.compose


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CategoryRepository
import com.example.compose.data.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel() {

    fun getAllCategories() = categoryRepository.getAllCategories()

    fun  addCategory (category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }

}