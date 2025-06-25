package ru.maxx52.androidsprint.ui.recipes.favorites

import androidx.lifecycle.ViewModel
import ru.maxx52.androidsprint.model.Recipe

class FavoritesViewModel : ViewModel() {
    data class FavoritesState(
        val favoriteRecipes: List<Recipe>? = emptyList()
    )

    private val favoritesState = FavoritesState()
}