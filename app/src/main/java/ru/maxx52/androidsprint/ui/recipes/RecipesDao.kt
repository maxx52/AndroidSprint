package ru.maxx52.androidsprint.ui.recipes

import androidx.room.*
import ru.maxx52.androidsprint.model.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE categoryId = :categoryId")
    suspend fun getRecipesByCategoryId(categoryId: Int): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes WHERE categoryId = :categoryId")
    suspend fun clearRecipesForCategory(categoryId: Int)

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    suspend fun getFavoriteRecipes(): List<Recipe>
}