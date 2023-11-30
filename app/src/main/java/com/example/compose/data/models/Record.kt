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
    @ColumnInfo(name = "src")
    val category: Int,
    @ColumnInfo(name = "amount")
    val amount: Float,
    @ColumnInfo(name = "category_type")
    val type: String,
)
