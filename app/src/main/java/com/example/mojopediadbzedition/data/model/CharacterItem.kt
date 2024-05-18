package com.example.mojopediadbzedition.data.model

import com.squareup.moshi.Json

class CharacterItem(
    val id: Int,

    @Json(name = "name")
    val characterName: String,

    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,

    @Json(name = "image")
    val characterImage: String,

    val affiliation: String,
    val deletedAt: String,
)