package com.example.zalmanach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity("planets_table")
data class Planet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @Json(name = "name")
    val planetName: String,

    val isDestroyed: Boolean,

    @Json(name = "description")
    val descriptionPlanetSpain: String,

    @Json(name = "image")
    val planetImage: String,
)
