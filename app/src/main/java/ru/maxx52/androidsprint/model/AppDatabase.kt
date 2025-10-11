package ru.maxx52.androidsprint.model

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.maxx52.androidsprint.ui.categories.CategoriesDao

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
}