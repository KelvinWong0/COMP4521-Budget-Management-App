package com.example.compose.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val recordName: String?,
    @Embedded
    val category: Category,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "date")
    val date: Date,
)

data class DateWithRecords(
    @Embedded val records: List<Record>,
    val date: Date
)