package com.example.zalmanach.data.model

import com.squareup.moshi.Json

class Planets(

    @Json(name = "items")
    val listOfPlanets: List<Planet>,
)
