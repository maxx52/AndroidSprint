package ru.maxx52.androidsprint.ui.categories

import androidx.room.*
import ru.maxx52.androidsprint.model.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
}