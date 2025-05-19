package ru.maxx52.androidsprint.entities

data class Recipe(
    private val id: Int,
    private val title: String,
    private val imageUrl: String,
    private val ingredients: List<Ingredient>,
    private val method: List<String>,
)