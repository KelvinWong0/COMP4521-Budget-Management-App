package com.example.compose.data.converter

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class RecordConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun DateToString (date: LocalDate): String{
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun StringToDate (date: String): LocalDate{
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}