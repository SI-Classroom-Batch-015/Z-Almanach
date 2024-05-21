package com.example.mojopediadbzedition.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity("character_table")
class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @Json(name = "name")
    val characterName: String,

    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,

    @Json(name = "description")
    val descriptionSpain: String,

    @Json(name = "image")
    val characterImage: String,

    )