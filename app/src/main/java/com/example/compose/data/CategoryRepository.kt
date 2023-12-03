package com.example.compose.data

import androidx.lifecycle.LiveData
import com.example.compose.data.daos.CategoryDAO
import com.example.compose.data.models.Category


class CategoryRepository(private val categoryDAO: CategoryDAO) {

    val readAllData : LiveData<List<Category>>  = categoryDAO.getAll()

    suspend fun insertCategory(category: Category){
        categoryDAO.addCategory(category)
    }

    suspend fun clearCategory(){
        categoryDAO.nukeTable()
    }

    suspend fun deleteCategory(category: Category){
        categoryDAO.delete(category)
    }

    fun readCategoryByType(isIncome: Boolean) : LiveData<List<Category>>{
        return  categoryDAO.loadAllByType(isIncome)
    }


}