package com.example.compose.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.compose.data.models.Category
import com.example.compose.data.daos.CategoryDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDAO

    companion object {
        @Volatile
        private var instance: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, CategoryDatabase::class.java,  "category_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
        }
            /*
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context,CategoryDatabase::class.java, "category_database")
                            .build()
                }
            }
            return instance!!
             */
        }


    }
}