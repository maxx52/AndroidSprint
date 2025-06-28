package ru.maxx52.androidsprint.ui.recipes.recipelist

import androidx.lifecycle.ViewModel
import ru.maxx52.androidsprint.model.Category
import ru.maxx52.androidsprint.model.Recipe

class RecipesListViewModel : ViewModel() {
    data class RecipesListState(
        val recipes: List<Recipe>? = emptyList(),
        val categories: List<Category>? = emptyList()
    )

    private val recipesListState = RecipesListState()
}