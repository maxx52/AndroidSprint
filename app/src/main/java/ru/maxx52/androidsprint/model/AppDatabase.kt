package ru.maxx52.androidsprint.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.maxx52.androidsprint.ui.categories.CategoriesDao
import ru.maxx52.androidsprint.ui.recipes.RecipesDao
import ru.maxx52.androidsprint.ui.recipes.recipe.RecipeConverters

@Database(entities = [Category::class, Recipe::class], version = 1)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}