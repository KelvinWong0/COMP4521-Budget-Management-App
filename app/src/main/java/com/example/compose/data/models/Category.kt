package com.example.compose.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "img_src_id")
    val src: Int,
    @ColumnInfo(name = "category_type")
    val type: Boolean
)
