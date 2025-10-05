package ru.maxx52.androidsprint.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import okio.IOException
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.ActivityMainBinding
import ru.maxx52.androidsprint.model.RecipeApiClient

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding не доступен или null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            try {
                val categoriesResponse = RecipeApiClient.apiService.getCategories()
                if (categoriesResponse.isNotEmpty()) {
                    val categoryIds = categoriesResponse.map { it.id }

                    categoryIds.forEach { categoryId ->
                        launch {
                            val recipesResponse = RecipeApiClient.apiService.getRecipesByCategoryId(categoryId)
                            if (recipesResponse.isNotEmpty()) {
                                Log.d("API_RECIPES_RESPONSE", "Получены рецепты для категории $categoryId:\n${recipesResponse.joinToString { it.title }}")
                            } else {
                                Log.w("API_WARNING", "Нет рецептов для категории $categoryId.")
                            }
                        }
                    }
                } else {
                    Log.e("API_ERROR", "Категории отсутствуют в ответе.")
                }
            } catch (e: IOException) {
                Log.e("API_IO_EXCEPTION", "Ошибка ввода-вывода при получении данных:", e)
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Общая ошибка при запросе данных:", e)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            btnCategory.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.fragmentListCategories)
            }
            btnFavorites.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}