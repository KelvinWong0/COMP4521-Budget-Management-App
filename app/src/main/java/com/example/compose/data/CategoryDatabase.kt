package com.example.compose.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.compose.R
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

        private val PREPOPULATED_DATA = listOf(
            //Expenses
            Category(0, "Grocery"   , R.drawable.ic_cat_grocery         , false),
            Category(0, "Game"      , R.drawable.ic_cat_entertainment   , false),
            Category(0, "Medication", R.drawable.ic_cat_medical         , false),
            Category(0, "Meal"      , R.drawable.ic_cat_restaurant      , false),
            Category(0, "Transport" , R.drawable.ic_cat_traffic         , false),
            Category(0, "Travel"    , R.drawable.ic_cat_travel          , false),

            //Incomes
            Category(0, "Savings"    , R.drawable.ic_cat_savings        , true),
            Category(0, "Gifts"      , R.drawable.ic_cat_gift           , true),
            Category(0, "Investment" , R.drawable.ic_cat_investment     , true),
            Category(0, "Salary"     , R.drawable.ic_cat_salary         , true),
            Category(0, "Dividend"   , R.drawable.ic_cat_dividend       , true),
            )

        fun getInstance(context: Context): CategoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, CategoryDatabase::class.java,  "category_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // insert the data on the IO Thread
                            ioThread {
                                getInstance(context).categoryDao().initializeCategories(PREPOPULATED_DATA)
                            }
                        }
                    })
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