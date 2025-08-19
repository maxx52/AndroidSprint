package ru.maxx52.androidsprint.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.FAVORITES_KEY
import ru.maxx52.androidsprint.data.PREFS_NAME
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.model.Recipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData(FavoritesState())
    val state: LiveData<FavoritesState> = _state

    fun loadFavoriteRecipes() {
        viewModelScope.launch {
            val favoritesSet = getFavorites()
            val favoritesIds = favoritesSet.mapNotNull { it.toIntOrNull() }.toSet()
            val favoriteRecipes = STUB.getRecipesByIds(favoritesIds)
            _state.value = FavoritesState(recipes = favoriteRecipes)
        }
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    data class FavoritesState(
        val recipes: List<Recipe> = emptyList()
    )
}