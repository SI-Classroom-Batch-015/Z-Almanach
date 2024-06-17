package com.example.zalmanach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val favoriteId: Int = 0,

    val favoriteType: String,

    val favoriteName: String,

    val favoriteImage: String,
)
