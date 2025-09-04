package ru.maxx52.androidsprint.ui.categories

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maxx52.androidsprint.model.Category
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.model.RecipesRepository

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipesRepository()
    private val _state = MutableLiveData(CategoriesState())
    val state: LiveData<CategoriesState> = _state

    fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = repository.getCategories()
                if (categories == null || categories.isEmpty()) {
                    showToast()
                } else {
                    _state.postValue(CategoriesState(categories = categories))
                }
            } catch (e: Exception) {
                Log.e("CATEGORIES_VIEW_MODEL", "Ошибка загрузки категорий", e)
                showToast()
            }
        }
    }

    private fun showToast() {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(getApplication(), "Ошибка получения данных", Toast.LENGTH_SHORT).show()
        }
    }

    data class CategoriesState(
        val categories: List<Category> = emptyList()
    )
}