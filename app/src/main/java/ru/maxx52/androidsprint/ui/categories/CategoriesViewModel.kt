package ru.maxx52.androidsprint.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maxx52.androidsprint.data.STUB
import ru.maxx52.androidsprint.model.Category
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableLiveData(CategoriesState())
    val state: LiveData<CategoriesState> = _state

    fun loadCategories() {
        viewModelScope.launch {
            val categories = STUB.getCategories()
            _state.value = CategoriesState(categories = categories)
        }
    }

    data class CategoriesState(
        val categories: List<Category> = emptyList()
    )
}