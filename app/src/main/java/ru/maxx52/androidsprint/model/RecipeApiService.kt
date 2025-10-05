package ru.maxx52.androidsprint.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipe/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe

    @GET("recipes")
    suspend fun getRecipesByIds(@Query("ids") ids: Set<Int>): List<Recipe>

    @GET("category/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Category

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategoryId(@Path("id") id: Int): List<Recipe>

    @GET("category")
    suspend fun getCategories(): List<Category>
}