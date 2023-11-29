package com.example.compose.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compose.data.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategories(categories: List<Category>)

    @Insert
    suspend fun addCategory(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAll(): LiveData<List<Category>>

    @Query("SELECT * FROM category_table WHERE category_type LIKE :type")
    fun loadAllByType(type : String): LiveData<List<Category>>

}