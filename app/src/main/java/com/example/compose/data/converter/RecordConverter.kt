package com.example.compose.data.converter

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.SimpleTimeZone

class RecordConverter {

    fun DateToString (date: Date): String{
        return date.toString()
    }


    fun StringToDate (date: String): Date{
        return SimpleDateFormat("dd-mm-yyyy").parse(date)
    }
}