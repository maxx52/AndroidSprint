package ru.maxx52.androidsprint.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maxx52.androidsprint.model.Category
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.model.RecipesRepository

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData<CategoriesState>()
    val state: LiveData<CategoriesState> = _state
    private val repository: RecipesRepository = RecipesRepository()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val cachedCategories = repository.getCategoriesFromCache()
                if (cachedCategories.isNotEmpty()) {
                    Log.i("!!!", "Загружено ${cachedCategories.size} категорий из кэша.")
                    _state.postValue(CategoriesState(categories = cachedCategories))
                }

                val serverCategories = repository.getCategories()
                if (serverCategories != null && serverCategories.isNotEmpty()) {
                    Log.i("!!!", "Получено ${serverCategories.size} категорий с сервера.")
                    repository.addCategories(serverCategories)
                    _state.postValue(CategoriesState(categories = serverCategories))
                }
            } catch (e: Exception) {
                Log.e("!!!", "Ошибка загрузки категорий", e)
            }
        }
    }

    fun findCategoryById(categoryId: Int): Category? {
        val categories = state.value?.categories
        return categories?.firstOrNull { it.id == categoryId }
    }

    data class CategoriesState(
        val categories: List<Category> = emptyList()
    )
}