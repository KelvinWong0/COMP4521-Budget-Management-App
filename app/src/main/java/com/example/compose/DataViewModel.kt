package com.example.compose


import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val readRecordsByDate: LiveData<Map<Date, List<Record>>>
    fun readAllRecordInCategory(categoryId : Int): LiveData<List<Record>>{
        return recordRepo.readRecordsInCategory(categoryId)
    }

    val getTotalExpense: LiveData<Int>
    val getTotalIncome: LiveData<Int>

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
        readRecordsByDate = recordRepo.readRecordsByDate

        getTotalExpense = recordRepo.getTotalExpense
        getTotalIncome = recordRepo.getTotalIncome

//        incomeAndExpense = getIncome.asFlow().combine(getExpense.asFlow()){
//            income, expense ->
//            income to expense
//        }.asLiveData()
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

    fun deleteCategory (category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepo.deleteCategory(category)
            recordRepo.deleteRecordsInCategory(category.categoryId)
        }
    }

    fun readCategoryByType(isIncome: Boolean) = categoryRepo.readCategoryByType(isIncome)

    fun readMonthWithRecordsByType(startOfMonth: Date, startOfNextMonth: Date, isIncome: Boolean) = recordRepo.readMonthWithRecordsByType(startOfMonth, startOfNextMonth,isIncome)

    fun readMonthWithRecords(startOfMonth: Date, startOfNextMonth: Date) = recordRepo.readMonthWithRecords(startOfMonth, startOfNextMonth)

    fun sumDayRecordsByType(startOfDay: Date, startOfNextDay: Date, isIncome: Boolean) = recordRepo.sumDayRecordsByType(startOfDay, startOfNextDay,isIncome)
    fun deleteRecord(record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepo.deleteRecord(record)
        }
    }


}