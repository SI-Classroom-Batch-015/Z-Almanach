package com.example.zalmanach.data.model

import com.squareup.moshi.Json

class Characters(

    @Json(name = "items")
    val listOfCharacters: List<Character>,
)