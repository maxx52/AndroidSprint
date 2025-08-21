package ru.maxx52.androidsprint.ui.recipes.recipelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.model.Recipe

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData(RecipesListState())
    val state: LiveData<RecipesListState> = _state

    fun loadRecipesByCategory(categoryId: Int) {
        viewModelScope.launch {
            val recipes = STUB.getRecipesByCategoryId(categoryId)
            _state.value = RecipesListState(recipes = recipes)
        }
    }

    data class RecipesListState(
        val recipes: List<Recipe> = emptyList()
    )
}