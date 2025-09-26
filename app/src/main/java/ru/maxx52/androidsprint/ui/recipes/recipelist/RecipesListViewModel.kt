package ru.maxx52.androidsprint.ui.recipes.recipelist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.NON_RECIPE
import ru.maxx52.androidsprint.data.repository
import ru.maxx52.androidsprint.model.Recipe
import ru.maxx52.androidsprint.ui.RecipesListFragmentDirections

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData(RecipesListState())
    val state: LiveData<RecipesListState> = _state

    fun loadRecipesByCategory(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = repository.getRecipesByCategoryId(categoryId)
                recipes?.let { _state.postValue(RecipesListState(recipes = it)) }
            } catch (e: Exception) {
                Log.e("RECIPES_LIST_VIEW_MODEL", "Ошибка загрузки рецептов", e)
            }
        }
    }

    data class RecipesListState(
        val recipes: List<Recipe> = emptyList()
    )
}