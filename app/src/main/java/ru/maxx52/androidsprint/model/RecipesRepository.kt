package ru.maxx52.androidsprint.model

import android.content.Context
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesRepository {
    private val baseUrl = "https://recipes.androidsprint.ru/api/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipeApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getRecipeById(id: Int): Recipe? {
        val call = recipeApiService.getRecipeById(id)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getRecipesByIds(ids: Set<Int>): List<Recipe>? {
        val call = recipeApiService.getRecipesByIds(ids)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getCategoryById(context: Context, id: Int): Category? {
        val call = recipeApiService.getCategoryById(id)
        try {
            val response = call.execute()
            if (!response.isSuccessful) {
                Toast.makeText(context, "Ошибка загрузки категории (${response.code()}): ${response.message()}", Toast.LENGTH_SHORT).show()
                return null
            }
            return response.body()
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка при загрузке категории: ${e.message}", Toast.LENGTH_SHORT).show()
            return null
        }
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        val call = recipeApiService.getRecipesByCategoryId(id)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getCategories(): List<Category>? {
        val call = recipeApiService.getCategories()
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }
}