package ru.maxx52.androidsprint.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import ru.maxx52.androidsprint.model.Ingredient

class RecipeViewModel : ViewModel() {

    data class RecipeState(
        val title: String? = "",
        val ingredients: List<Ingredient> = emptyList(),
        val method: List<String> = emptyList(),
    )

    private val recipeState = RecipeState()
}