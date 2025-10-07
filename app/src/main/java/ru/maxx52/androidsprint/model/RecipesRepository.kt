package ru.maxx52.androidsprint.model

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.maxx52.androidsprint.data.BASE_URL
import ru.maxx52.androidsprint.data.DATABASE_NAME
import ru.maxx52.androidsprint.ui.categories.CategoriesDao

class RecipesRepository() {
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            RecipeApplication.instance.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    private val categoriesDao: CategoriesDao = db.categoriesDao()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipeApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getRecipeById(id: Int): Recipe? =
        withContext(Dispatchers.IO) {
            runCatching { recipeApiService.getRecipeById(id) }.getOrNull()
        }

    suspend fun getRecipesByIds(ids: Set<Int>): List<Recipe>? =
        withContext(Dispatchers.IO) {
            runCatching { recipeApiService.getRecipesByIds(ids) }.getOrNull()
        }

    suspend fun getCategoryById(id: Int): Category? =
        withContext(Dispatchers.IO) {
            runCatching { recipeApiService.getCategoryById(id) }.getOrNull()
        }

    suspend fun getRecipesByCategoryId(id: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            runCatching { recipeApiService.getRecipesByCategoryId(id) }.getOrNull()
        }

    suspend fun getCategories(): List<Category>? =
        withContext(Dispatchers.IO) {
            runCatching { recipeApiService.getCategories() }.getOrNull()
        }

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesDao.getAllCategories()
        }
    }

    suspend fun addCategories(categories: List<Category>) {
        withContext(Dispatchers.IO) {
            categoriesDao.insertCategories(categories)
        }
    }
}