package ru.maxx52.androidsprint.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "recipes")
@Parcelize
data class Recipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val categoryId: Int,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
) : Parcelable