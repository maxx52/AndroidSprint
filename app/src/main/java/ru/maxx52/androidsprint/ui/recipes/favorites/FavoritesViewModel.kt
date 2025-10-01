package ru.maxx52.androidsprint.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.FAVORITES_KEY
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.data.PREFS_NAME
import ru.maxx52.androidsprint.data.repository
import ru.maxx52.androidsprint.model.Recipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData<FavoritesState>()
    val state: LiveData<FavoritesState> = _state

    fun loadFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favoritesSet = getFavorites()
                val favoritesIds = favoritesSet.mapNotNull { it.toIntOrNull() }.toSet()
                val favoriteRecipes = repository.getRecipesByIds(favoritesIds)
                favoriteRecipes?.let { _state.postValue(FavoritesState(recipes = it)) }
            } catch (e: Exception) {
                Log.e("FAVORITES_VIEW_MODEL", "Ошибка загрузки любимых рецептов", e)
            }
        }
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipe = repository.getRecipeById(recipeId)
                if (recipe != null) {
                    _state.postValue(FavoritesState(navigateToRecipe = true, recipeId = recipeId))
                } else {
                    _state.postValue(FavoritesState(error = NON_RECIPE))
                }
            } catch (e: Exception) {
                Log.e("FAVORITES_VIEW_MODEL", "Ошибка загрузки рецепта", e)
                _state.postValue(FavoritesState(error = "Ошибка загрузки рецепта"))
            }
        }
    }

    data class FavoritesState(
        val recipes: List<Recipe> = emptyList(),
        val navigateToRecipe: Boolean = false,
        val error: String? = null,
        val recipeId: Int? = null
    )
}