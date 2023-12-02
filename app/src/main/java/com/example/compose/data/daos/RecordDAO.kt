package com.example.compose.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compose.data.models.Record

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

    @Query("DELETE FROM record_table")
    suspend fun nukeTable()

    @Query("SELECT * FROM record_table")
    fun getAll(): LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE category_type LIKE :isIncome")
    fun loadAllByType(isIncome : Boolean): LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE category_type LIKE :isIncome AND date LIKE ('-'+ :month + '-')")
    fun loadAllRecordsDatedByType(month: String, isIncome: Boolean): LiveData<List<Record>>
}

