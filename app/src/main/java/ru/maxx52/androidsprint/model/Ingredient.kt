package ru.maxx52.androidsprint.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable