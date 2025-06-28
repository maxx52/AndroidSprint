package ru.maxx52.androidsprint.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maxx52.androidsprint.model.Ingredient
import ru.maxx52.androidsprint.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeState(
        val recipe: Recipe? = null,
        val currentPortions: Int = 1,
        val ingredients: List<Ingredient> = emptyList()
    )

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    private val _portions = MutableLiveData(1)
    val portions: LiveData<Int> = _portions

    fun changePortions(portions: Int) {
        _portions.value = portions
        recalculateIngredients()
    }

    private fun recalculateIngredients() {
        val recipe = recipe.value ?: return
        val portions = portions.value?.toBigDecimal() ?: return

        val newIngredients = recipe.ingredients.map { ingr ->
            Ingredient(
                description = ingr.description,
                quantity = ingr.quantity.toBigDecimal().multiply(portions).toString(),
                unitOfMeasure = ingr.unitOfMeasure
            )
        }

        _recipe.value = recipe.copy(ingredients = newIngredients)
    }
}