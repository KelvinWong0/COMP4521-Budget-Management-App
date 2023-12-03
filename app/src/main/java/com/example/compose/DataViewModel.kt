package com.example.compose


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CategoryDatabase
import com.example.compose.data.CategoryRepository
import com.example.compose.data.RecordDatabase
import com.example.compose.data.RecordRepository
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class DataViewModel(application: Application): AndroidViewModel(application) {


    val readAllCategory: LiveData<List<Category>>
    val readAllRecord: LiveData<List<Record>>
    val readAllExpense: LiveData<List<Record>>
    val readAllIncome: LiveData<List<Record>>

    private val categoryRepo : CategoryRepository
    private val recordRepo : RecordRepository

    init {
        val categoryDao = CategoryDatabase.getInstance(application).categoryDao()
        val recordDao = RecordDatabase.getInstance(application).recordDao()
        categoryRepo = CategoryRepository(categoryDao)
        recordRepo = RecordRepository(recordDao)
        readAllCategory = categoryRepo.readAllData
        readAllRecord = recordRepo.readAllData
        readAllExpense = recordRepo.readAllExpense
        readAllIncome = recordRepo.readAllIncome
    }

    fun addRecord (record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepo.insertRecord(record)
        }
    }

    fun  addCategory (category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepo.insertCategory(category)
        }
    }

    fun clearRecordTable (){
        viewModelScope.launch(Dispatchers.IO) {
            recordRepo.clearRecord()
        }
    }
    fun clearCategoryTable (){
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepo.clearCategory()
        }
    }

    fun readCategoryByType(isIncome: Boolean) = categoryRepo.readCategoryByType(isIncome)

    fun readDatesWithRecordsByType( startOfMonth: Date, startOfNextMonth: Date,isIncome: Boolean) = recordRepo.readAllDateWithRecordsByType(startOfMonth, startOfNextMonth,isIncome)

}