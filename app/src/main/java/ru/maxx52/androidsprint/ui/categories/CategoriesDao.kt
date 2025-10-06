package ru.maxx52.androidsprint.ui.categories

import androidx.room.*
import ru.maxx52.androidsprint.model.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)
}