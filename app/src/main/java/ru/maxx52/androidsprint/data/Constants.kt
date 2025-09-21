package ru.maxx52.androidsprint.data

import ru.maxx52.androidsprint.model.RecipesRepository

const val NON_RECIPE = "Рецепт не найден"
const val PREFS_NAME = "recipe_favorites_prefs"
const val FAVORITES_KEY = "favorite_recipes_set"
val repository: RecipesRepository = RecipesRepository()
const val IMAGE_BASE_URL = "https://recipes.androidsprint.ru/api"