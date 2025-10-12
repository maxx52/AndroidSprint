package ru.maxx52.androidsprint.ui.recipes.recipe

import ru.maxx52.androidsprint.model.Ingredient

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RecipeConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromIngredientsString(value: String): List<Ingredient> {
        val listType: Type = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIngredientsList(list: List<Ingredient>): String {
        return Gson().toJson(list)
    }
}