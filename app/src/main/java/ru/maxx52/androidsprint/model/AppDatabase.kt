package ru.maxx52.androidsprint.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.maxx52.androidsprint.ui.categories.CategoriesDao
import ru.maxx52.androidsprint.ui.recipes.RecipesDao
import ru.maxx52.androidsprint.ui.recipes.recipe.RecipeConverters

@Database(entities = [Category::class, Recipe::class], version = 3)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}

val DB_MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE recipes ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
    }
}