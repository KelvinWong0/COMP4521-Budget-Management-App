package com.example.compose.data

import androidx.lifecycle.LiveData
import com.example.compose.data.daos.RecordDAO
import com.example.compose.data.models.Record
import java.util.Date


class RecordRepository(private val recordDAO: RecordDAO) {

    val readAllData : LiveData<List<Record>>  = recordDAO.getAll()
    val readAllExpense: LiveData<List<Record>>  = recordDAO.loadAllByType(false)
    val readAllIncome: LiveData<List<Record>>  = recordDAO.loadAllByType(true)

    suspend fun insertRecord(record: Record){
        recordDAO.addRecord(record)
    }

    suspend fun clearRecord(){
        recordDAO.nukeTable()
    }

    fun readAllDateWithRecordsByType(startOfMonth: Date, startOfNextMonth: Date, isIncome:Boolean): LiveData<List<Record>>{
        return recordDAO.loadAllRecordsinMonthByType(startOfMonth, startOfNextMonth, isIncome)
    }
}