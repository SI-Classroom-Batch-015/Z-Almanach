package com.example.mojopediadbzedition.data.remote

import com.example.mojopediadbzedition.data.model.Characters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Die Konstante enthält die URL der API
const val BASE_URL = "https://dragonball-api.com/api/"


// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit übernimmt die Kommunikation und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DbzApiservice {

    @GET("characters")
    suspend fun getCharacters(): Characters
}

// Das Objekt dient als Zugangspunkt für den Rest der App und stellt den API Service zur Verfügung
object DbzApi {
    val retrofitService: DbzApiservice by lazy { retrofit.create(DbzApiservice::class.java) }
}