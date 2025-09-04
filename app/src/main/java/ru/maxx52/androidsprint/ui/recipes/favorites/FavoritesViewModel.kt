package ru.maxx52.androidsprint.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.FAVORITES_KEY
import ru.maxx52.androidsprint.data.PREFS_NAME
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.model.RecipesRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipesRepository()

    private val _state = MutableLiveData(FavoritesState())
    val state: LiveData<FavoritesState> = _state

    fun loadFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favoritesSet = getFavorites()
                val favoritesIds = favoritesSet.mapNotNull { it.toIntOrNull() }.toSet()
                val favoriteRecipes = repository.getRecipesByIds(favoritesIds)
                if (favoriteRecipes == null || favoriteRecipes.isEmpty()) {
                    showToast()
                } else {
                    _state.postValue(FavoritesState(recipes = favoriteRecipes))
                }
            } catch (e: Exception) {
                Log.e("FAVORITES_VIEW_MODEL", "Ошибка загрузки любимых рецептов", e)
                showToast()
            }
        }
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    private fun showToast() {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(getApplication(), "Ошибка получения данных", Toast.LENGTH_SHORT).show()
        }
    }

    data class FavoritesState(
        val recipes: List<Recipe> = emptyList()
    )
}