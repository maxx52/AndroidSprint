package ru.maxx52.androidsprint.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class RecipesRepository {
    private val baseUrl = "https://recipes.androidsprint.ru/api/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
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