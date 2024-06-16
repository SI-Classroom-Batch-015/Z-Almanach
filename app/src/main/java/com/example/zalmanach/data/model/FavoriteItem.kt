package com.example.zalmanach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteItem(
    @PrimaryKey(autoGenerate = true)

    val itemFavoriteId: Int = 0,
    val itemFavoriteType: String,
    val itemFavoriteName: String,
    val itemFavoriteImage: String,
)
