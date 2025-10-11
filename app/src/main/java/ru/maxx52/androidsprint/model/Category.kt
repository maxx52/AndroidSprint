package ru.maxx52.androidsprint.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
@Serializable
@Parcelize
data class Category(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?,
) : Parcelable
