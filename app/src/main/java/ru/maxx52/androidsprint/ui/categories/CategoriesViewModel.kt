package ru.maxx52.androidsprint.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maxx52.androidsprint.model.Category
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.repository

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData(CategoriesState())
    val state: LiveData<CategoriesState> = _state

    fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = repository.getCategories()
                categories?.let { _state.postValue(CategoriesState(categories = it)) }
            } catch (e: Exception) {
                Log.e("CATEGORIES_VIEW_MODEL", "Ошибка загрузки категорий", e)
            }
        }
    }

    data class CategoriesState(
        val categories: List<Category> = emptyList()
    )
}