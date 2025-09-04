package ru.maxx52.androidsprint.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.ActivityMainBinding
import ru.maxx52.androidsprint.model.Category
import ru.maxx52.androidsprint.model.Recipe
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding не доступен или null")
    private val threadPool = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val threadName = Thread.currentThread().name
        Log.d("LIFECYCLE", "Метод onCreate() выполняется на потоке: $threadName")

        threadPool.execute {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val threadName = Thread.currentThread().name

            Log.d("NETWORK_REQUEST", "Выполняю запрос на потоке: $threadName")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val content = reader.readLines().joinToString(separator = "\n")
                    reader.close()

                    val gson = Gson()
                    val type = object : TypeToken<List<Category>>() {}.type
                    val categories = gson.fromJson<List<Category>>(content, type)
                    val categoryIds = categories.map { it.id }

                    categoryIds.forEach { categoryId ->
                        threadPool.execute {
                            val recipesUrl = URL("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
                            val recipesConnection = recipesUrl.openConnection() as HttpURLConnection
                            recipesConnection.requestMethod = "GET"
                            try {
                                val recipesResponseCode = recipesConnection.responseCode
                                if (recipesResponseCode == HttpURLConnection.HTTP_OK) {
                                    val recipesReader = BufferedReader(InputStreamReader(recipesConnection.inputStream))
                                    val recipesContent = recipesReader.readLines().joinToString(separator = "\n")
                                    recipesReader.close()

                                    val recipesGson = Gson()
                                    val recipesType = object : TypeToken<List<Recipe>>() {}.type
                                    val recipes = recipesGson.fromJson<List<Recipe>>(recipesContent, recipesType)

                                    Log.d("API_RECIPES_RESPONSE", "Получены рецепты для категории $categoryId:\n${recipes.joinToString { it.title }}")
                                } else {
                                    Log.e("API_RECIPES_ERROR", "Ошибка при запросе рецептов для категории $categoryId: Код ответа $recipesResponseCode")
                                }
                            } finally {
                                recipesConnection.disconnect()
                            }
                        }
                    }
                } else {
                    Log.e("API_ERROR", "Request failed with code: $responseCode")
                }
            } finally {
                connection.disconnect()
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