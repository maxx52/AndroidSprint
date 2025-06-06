package ru.maxx52.androidsprint.entities

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    private val ingredients: List<Ingredient>,
    private val method: List<String>,
) : Parcelable