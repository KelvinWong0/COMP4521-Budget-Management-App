package com.example.compose.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.compose.data.models.DateWithRecords
import com.example.compose.data.models.Record
import java.util.Date

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

    @Query("DELETE FROM record_table WHERE categoryId  =  :categoryId")
    suspend fun deleteRecordsInCategory(categoryId: Int)

    @Query("SELECT * FROM record_table")
    fun getAll(): LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE category_type = :isIncome")
    fun loadAllByType(isIncome : Boolean): LiveData<List<Record>>


    @Query("SELECT * FROM record_table WHERE category_type LIKE :isIncome AND date >= :startOfMonth AND date < :startOfNextMonth")
    fun loadAllRecordsinMonthByType(startOfMonth: Date, startOfNextMonth: Date, isIncome: Boolean): LiveData<List<Record>>
    @Query("SELECT * FROM record_table WHERE date >= :startOfMonth AND date < :startOfNextMonth")
    fun loadAllRecordsinMonth(startOfMonth: Date, startOfNextMonth: Date): LiveData<List<Record>>

    @Query("SELECT SUM(CASE WHEN category_type = :isIncome THEN amount ELSE -amount END) FROM record_table WHERE date >= :startOfDay AND date < :startOfNextDay GROUP BY date")
    //@Query("SELECT SUM(amount) FROM record_table WHERE category_type = :isIncome AND date >= :startOfDay AND date < :startOfNextDay")
    fun sumAllRecordsinDayByType(startOfDay: Date, startOfNextDay: Date, isIncome: Boolean): LiveData<Int>//not working

    @Query("SELECT SUM(amount) FROM record_table WHERE category_type = :isIncome")
    fun sumOfRecordsByCategory (isIncome: Boolean): LiveData<Int>
    @Query("SELECT * From record_table Where categoryId = :categoryId")
    fun loadAllRecordsInCategory(categoryId: Int) : LiveData<List<Record>>


    @Query("SELECT date(ra.date/1000, 'unixepoch')as date,  *  FROM record_table ra Join record_table rb on date(ra.date/1000, 'unixepoch') = date(rb.date/1000, 'unixepoch') ")
    fun getRecordsByDate(): LiveData<Map<@MapColumn("date") Date, List<Record>>>
}

