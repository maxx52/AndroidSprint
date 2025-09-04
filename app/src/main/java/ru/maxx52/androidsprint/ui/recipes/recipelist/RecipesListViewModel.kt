package ru.maxx52.androidsprint.ui.recipes.recipelist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.model.RecipesRepository

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipesRepository()

    private val _state = MutableLiveData(RecipesListState())
    val state: LiveData<RecipesListState> = _state

    fun loadRecipesByCategory(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = repository.getRecipesByCategoryId(categoryId)
                if (recipes == null || recipes.isEmpty()) {
                    showToast()
                } else {
                    _state.postValue(RecipesListState(recipes = recipes))
                }
            } catch (e: Exception) {
                Log.e("RECIPES_LIST_VIEW_MODEL", "Ошибка загрузки рецептов", e)
                showToast()
            }
        }
    }

    private fun showToast() {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(getApplication(), "Ошибка получения данных", Toast.LENGTH_SHORT).show()
        }
    }

    data class RecipesListState(
        val recipes: List<Recipe> = emptyList()
    )
}