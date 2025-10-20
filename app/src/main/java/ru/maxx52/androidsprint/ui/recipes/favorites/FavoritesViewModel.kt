package ru.maxx52.androidsprint.ui.recipes.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.data.repository
import ru.maxx52.androidsprint.model.Recipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData<FavoritesState>()
    val state: LiveData<FavoritesState> = _state

    fun loadFavoriteRecipes() {
        viewModelScope.launch {
            try {
                val favoriteRecipes = repository.getFavoriteRecipes()
                _state.postValue(FavoritesState(recipes = favoriteRecipes))
            } catch (e: Exception) {
                Log.e("FAVORITE_RECIPES_VIEW_MODEL", "Ошибка загрузки избранных рецептов", e)
                _state.postValue(FavoritesState(error = "Ошибка загрузки"))
            }
        }
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        viewModelScope.launch {
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