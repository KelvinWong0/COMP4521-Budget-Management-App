package com.example.compose.data

import androidx.lifecycle.LiveData
import com.example.compose.data.daos.RecordDAO
import com.example.compose.data.models.Record
import java.util.Date


class RecordRepository(private val recordDAO: RecordDAO) {

    val readRecordsByDate: LiveData<Map<Date, List<Record>>> = recordDAO.getRecordsByDate()
    fun readRecordsInCategory(categoryId: Int): LiveData<List<Record>> {
        return recordDAO.loadAllRecordsInCategory(categoryId)
    }
    val readAllData : LiveData<List<Record>>  = recordDAO.getAll()
    val readAllExpense: LiveData<List<Record>>  = recordDAO.loadAllByType(false)
    val readAllIncome: LiveData<List<Record>>  = recordDAO.loadAllByType(true)

    suspend fun insertRecord(record: Record){
        recordDAO.addRecord(record)
    }

    suspend fun clearRecord(){
        recordDAO.nukeTable()
    }

    suspend fun deleteRecordsInCategory(categoryId: Int){
        recordDAO.deleteRecordsInCategory(categoryId)
    }

    fun readMonthWithRecordsByType(startOfMonth: Date, startOfNextMonth: Date, isIncome:Boolean): LiveData<List<Record>>{
        return recordDAO.loadAllRecordsinMonthByType(startOfMonth, startOfNextMonth, isIncome)
    }

    fun readMonthWithRecords(startOfMonth: Date, startOfNextMonth: Date): LiveData<List<Record>>{
        return recordDAO.loadAllRecordsinMonth(startOfMonth, startOfNextMonth)
    }

    fun sumDayRecordsByType(startOfDay: Date, startOfNextDay: Date, isIncome:Boolean): LiveData<Int>{
        return recordDAO.sumAllRecordsinDayByType(startOfDay, startOfNextDay, isIncome)
    }

    suspend fun deleteRecord(record: Record) {
        recordDAO.delete(record)
    }

    val getTotalExpense: LiveData<Int> = recordDAO.sumOfRecordsByCategory(false)
    val getTotalIncome: LiveData<Int> = recordDAO.sumOfRecordsByCategory(true)
}