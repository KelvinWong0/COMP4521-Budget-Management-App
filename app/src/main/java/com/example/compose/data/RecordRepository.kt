package com.example.compose.data

import androidx.lifecycle.LiveData
import com.example.compose.data.daos.CategoryDAO
import com.example.compose.data.daos.RecordDAO
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import java.util.Date


class RecordRepository(private val recordDAO: RecordDAO) {

    val readAllData : LiveData<List<Record>>  = recordDAO.getAll()

    suspend fun insertRecord(record: Record){
        recordDAO.addRecord(record)
    }

    suspend fun clearRecord(){
        recordDAO.nukeTable()
    }

    fun readAllDateWithRecordsByType(month: Date, isIncome:Boolean){
        recordDAO.loadAllRecordsDatedByType(month, isIncome)
    }
}