package ru.maxx52.androidsprint.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesApiService {

    // Получение рецепта по ID
    @GET("/recipe/{id}")
    fun getRecipeById(@Path("id") id: Int): Call<Recipe>

    // Получение списка рецептов по списку ID
    @GET("/recipes")
    fun getRecipesByIds(@Query("ids") ids: String): Call<List<Recipe>>

    // Получение категории по ID
    @GET("/category/{id}")
    fun getCategoryById(@Path("id") id: Int): Call<Category>

    // Получение списка рецептов в категории по ID категории
    @GET("/category/{id}/recipes")
    fun getRecipesByCategoryId(@Path("id") id: Int): Call<List<Recipe>>

    // Получение списка всех категорий
    @GET("/category")
    fun getCategories(): Call<List<Category>>
}