package com.example.compose.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "img_src")
    val src: Int,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "type")
    val type: String
)
