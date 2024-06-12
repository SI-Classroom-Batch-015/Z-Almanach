package com.example.zalmanach.data.remote

import com.example.zalmanach.data.model.Characters
import com.example.zalmanach.data.model.Planets
import com.example.zalmanach.data.model.Transformation
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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

interface DbzApiService {

    @GET("characters")
    suspend fun getCharacters(): Characters

    @GET("transformations")
    suspend fun getTransformations(): List<Transformation>

    @GET("planets")
    suspend fun getPlanets(): Planets

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") characterId: Int): Character
}

// Das Objekt dient als Zugangspunkt für den Rest der App und stellt den API Service zur Verfügung
object DbzApi {
    val retrofitService: DbzApiService by lazy { retrofit.create(DbzApiService::class.java) }
}