package com.example.zalmanach.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation

@Dao
interface DragonballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): LiveData<List<Character>>

    @Query("DELETE FROM character_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformations(transformations: List<Transformation>)

    @Query("SELECT * FROM transformation_table")
    fun getAllTransformations(): LiveData<List<Transformation>>

    @Query("DELETE FROM transformation_table")
    suspend fun deleteAllTransformations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanets(planets: List<Planet>)

    @Query("SELECT * FROM planets_table")
    fun getAllPlanets(): LiveData<List<Planet>>

    @Query("DELETE FROM planets_table")
    suspend fun deleteAllPlanets()

    // Methode für die Volltextsuche
    @Query("SELECT * FROM character_table WHERE characterName LIKE :query")
    fun searchCharacters(query: String): LiveData<List<Character>>

    @Query("SELECT * FROM transformation_table WHERE transformationName LIKE :query")
    fun searchTransformations(query: String): LiveData<List<Transformation>>

    @Query("SELECT * FROM planets_table WHERE planetName LIKE :query")
    fun searchPlanets(query: String): LiveData<List<Planet>>
}

