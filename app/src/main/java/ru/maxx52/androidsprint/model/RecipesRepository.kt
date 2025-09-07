package ru.maxx52.androidsprint.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesRepository {
    private val baseUrl = "https://recipes.androidsprint.ru/api/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipesApiService: RecipesApiService = retrofit.create(RecipesApiService::class.java)

    fun getRecipeById(id: Int): Recipe? {
        val call = recipesApiService.getRecipeById(id)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getRecipesByIds(ids: Set<Int>): List<Recipe>? {
        val call = recipesApiService.getRecipesByIds(ids)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getCategoryById(id: Int): Category? {
        val call = recipesApiService.getCategoryById(id)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getRecipesByCategoryId(id: Int): List<Recipe>? {
        val call = recipesApiService.getRecipesByCategoryId(id)
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }

    fun getCategories(): List<Category>? {
        val call = recipesApiService.getCategories()
        val response = call.execute()
        return if (response.isSuccessful) response.body() else null
    }
}