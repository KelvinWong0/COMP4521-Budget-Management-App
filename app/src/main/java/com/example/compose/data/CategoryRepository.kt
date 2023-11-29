package com.example.compose.data

import com.example.compose.data.models.Category


class CategoryRepository(private val categoryDatabase: CategoryDatabase) {

    private val categoryDao =  categoryDatabase.categoryDao()

    suspend fun insertCategory(category: Category){
        categoryDao.addCategory(category)
    }

    fun getAllCategories() = categoryDao.getAll()

}