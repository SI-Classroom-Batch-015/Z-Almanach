package com.example.zalmanach.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity("transformation_table")
data class Transformation(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,

    @Json(name = "name")
    val transformationName: String,

    @Json(name = "image")
    val transformationImage: String,

    @Json(name = "ki")
    val transformationKi: String,
) : DbzEntity
