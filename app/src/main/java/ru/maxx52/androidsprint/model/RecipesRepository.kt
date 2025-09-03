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

    fun getRecipeById(id: Int) = recipesApiService.getRecipeById(id)
    fun getRecipesByIds(ids: String) = recipesApiService.getRecipesByIds(ids)
    fun getCategoryById(id: Int) = recipesApiService.getCategoryById(id)
    fun getRecipesByCategoryId(id: Int) = recipesApiService.getRecipesByCategoryId(id)
    fun getCategories() = recipesApiService.getCategories()
}