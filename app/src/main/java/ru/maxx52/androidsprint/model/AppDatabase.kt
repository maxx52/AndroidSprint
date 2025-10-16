package ru.maxx52.androidsprint.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.maxx52.androidsprint.ui.categories.CategoriesDao
import ru.maxx52.androidsprint.ui.recipes.RecipesDao
import ru.maxx52.androidsprint.ui.recipes.recipe.RecipeConverters

@Database(entities = [Category::class, Recipe::class], version = 2)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}

val DB_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE recipes ADD COLUMN category_id INTEGER NOT NULL DEFAULT 0")
    }
}