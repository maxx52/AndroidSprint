package ru.maxx52.androidsprint.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maxx52.androidsprint.model.Ingredient
import ru.maxx52.androidsprint.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeState(
        val recipe: Recipe? = null,
        val currentPortions: Int = 1,
        val ingredients: List<Ingredient> = emptyList(),
        val isFavorite: Boolean = false
    )

    private val _state = MutableLiveData<RecipeState>()
    val state: LiveData<RecipeState> = _state

    private val _portions = MutableLiveData(1)
    val portions: LiveData<Int> = _portions

    init {
        Log.i("!!!", "Initializing ViewModel...")
        _state.value = RecipeState()
    }

    fun toggleFavorite() {
        val currentState = _state.value ?: return
        _state.value = currentState.copy(isFavorite = !currentState.isFavorite)
    }

    private fun recalculateIngredients() {
        val recipe = _state.value ?: return
        val portions = _portions.value?.toBigDecimal() ?: return

        val newIngredients = recipe.ingredients.map { ingr ->
            Ingredient(
                description = ingr.description,
                quantity = ingr.quantity.toBigDecimal().multiply(portions).toString(),
                unitOfMeasure = ingr.unitOfMeasure
            )
        }

        _state.value = recipe.copy(ingredients = newIngredients)
    }
}