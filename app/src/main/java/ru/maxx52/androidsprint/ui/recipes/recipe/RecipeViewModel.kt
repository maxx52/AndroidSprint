package ru.maxx52.androidsprint.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.FAVORITES_KEY
import ru.maxx52.androidsprint.data.PREFS_NAME
import ru.maxx52.androidsprint.model.Ingredient
import ru.maxx52.androidsprint.model.Recipe
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import ru.maxx52.androidsprint.data.BASE_URL
import ru.maxx52.androidsprint.data.repository

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeState(
        val recipe: Recipe? = null,
        val currentPortions: Int = 1,
        val ingredients: List<Ingredient> = emptyList(),
        val isFavorite: Boolean = false,
        val recipeImageUrl: String? = null
    )

    private val _state = MutableLiveData<RecipeState>()
    val state: LiveData<RecipeState> = _state

    fun loadRecipe(recipeId: Int) {
        Log.i("!!!", "Loading recipe with ID: $recipeId")
        viewModelScope.launch {
            try {
                val loadedRecipe = repository.getRecipeById(recipeId)
                if (loadedRecipe == null) {
                    Log.e("!!!", "Recipe with ID $recipeId was not found")
                    return@launch
                }

                val isFavorite = getFavorites().contains(recipeId.toString())
                val completeImageUrl = "$BASE_URL${loadedRecipe.imageUrl}"
                _state.postValue(RecipeState(
                    recipe = loadedRecipe,
                    isFavorite = isFavorite,
                    ingredients = loadedRecipe.ingredients,
                    currentPortions = 1,
                    recipeImageUrl = completeImageUrl
                ))
                Log.d("!!!", "Loaded recipe: ${loadedRecipe.title}, isFavorite: $isFavorite, ingredients size: ${loadedRecipe.ingredients.size}")
            } catch (e: Exception) {
                Log.e("!!!", "Ошибка загрузки рецепта", e)
            }
        }
    }

    init {
        Log.i("!!!", "Initializing ViewModel...")
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet<String>())?.toMutableSet() ?: mutableSetOf()
    }

    private fun saveFavorites(updatedFavorites: Set<String>) {
        val sharedPrefs = getApplication<Application>()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit { putStringSet(FAVORITES_KEY, updatedFavorites) }
    }

    fun onFavoritesClicked() {
        val currentState = _state.value ?: return
        val recipe = currentState.recipe ?: return
        val newIsFavorite = !currentState.isFavorite
        _state.value = currentState.copy(isFavorite = newIsFavorite)
        updateFavorites(recipe.id, newIsFavorite)
    }

    private fun updateFavorites(recipeId: Int, isFavorite: Boolean) {
        val favorites = getFavorites().toMutableSet()
        val recipeIdStr = recipeId.toString()

        if (isFavorite) {
            favorites.add(recipeIdStr)
        } else {
            favorites.remove(recipeIdStr)
        }
        saveFavorites(favorites)
    }

    fun recalculateIngredients() {
        val currentState = _state.value ?: return
        val portions: Int = currentState.currentPortions
        val originalIngredients = currentState.recipe?.ingredients ?: emptyList()
        val newIngredients = originalIngredients.map { ingr ->
            Ingredient(
                description = ingr.description,
                quantity = ingr.quantity.toBigDecimal().multiply(portions.toBigDecimal()).toString(),
                unitOfMeasure = ingr.unitOfMeasure
            )
        }
        _state.value = currentState.copy(ingredients = newIngredients)
    }

    fun updatePortions(newPortions: Int) {
        val currentState = _state.value ?: return
        _state.value = currentState.copy(currentPortions = newPortions)
    }
}