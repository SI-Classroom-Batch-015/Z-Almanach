package com.example.zalmanach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteItem(
    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,
    val itemType: String,
    val itemName: String,
    val itemImage: String,
)
