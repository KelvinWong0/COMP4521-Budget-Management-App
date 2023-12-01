package com.example.compose.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compose.data.models.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDAO{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecords(categories: List<Record>)

    @Insert
    suspend fun addRecord(record: Record)

    @Update
    suspend fun update(record: Record)

    @Delete
    suspend fun delete(record: Record)

    @Query("SELECT * FROM record_table")
    fun getAll(): LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE type LIKE :categoryType")
    fun loadAllByType(categoryType : String): LiveData<List<Record>>

}