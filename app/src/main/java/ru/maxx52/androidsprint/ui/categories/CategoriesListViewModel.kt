package ru.maxx52.androidsprint.ui.categories

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.model.Category
import ru.maxx52.androidsprint.model.RecipesRepository

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipesRepository(application)
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val cachedCategories = repository.getCategoriesFromCache()
            if (cachedCategories.isNotEmpty()) {
                _categories.postValue(cachedCategories)
            } else {
                loadCategoriesFromBackend()
            }
        }
    }

    private suspend fun loadCategoriesFromBackend() {
        val backendCategories = repository.getCategories()
        if (!backendCategories.isNullOrEmpty()) {
            repository.getCategoriesFromCache().forEach(repository::deleteCategory)
            backendCategories.forEach(repository::addCategory)
            _categories.postValue(backendCategories)
        }
    }

    fun refreshCategories() {
        viewModelScope.launch {
            loadCategoriesFromBackend()
        }
    }
}