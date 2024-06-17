package com.example.zalmanach.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Favorite
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation

@Dao
interface DragonballDao {

    // ------------------------------ Einfügen der Daten Liste (R/I) -------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformations(transformations: List<Transformation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanets(planets: List<Planet>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favoriteItem: Favorite)


    // ------------------------------ Abfrage der Daten als LiveData -------------------------------
    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): LiveData<List<Character>>

    @Query("SELECT * FROM transformation_table")
    fun getAllTransformations(): LiveData<List<Transformation>>

    @Query("SELECT * FROM planets_table")
    fun getAllPlanets(): LiveData<List<Planet>>

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): LiveData<List<Favorite>>


    // ------------------------------------------ Löschen ------------------------------------------
    @Query("DELETE FROM character_table")
    suspend fun deleteAll()

    @Query("DELETE FROM transformation_table")
    suspend fun deleteAllTransformations()

    @Query("DELETE FROM planets_table")
    suspend fun deleteAllPlanets()

    @Query("DELETE FROM favorite_table WHERE favoriteId = :itemFavoriteId")
    suspend fun deleteFavorite(itemFavoriteId: Int, itemFavoriteType: String)


    // ------------------------------------------- Suche -------------------------------------------
    @Query("SELECT * FROM character_table WHERE characterName LIKE :query")
    fun searchCharacters(query: String): LiveData<List<Character>>

    @Query("SELECT * FROM transformation_table WHERE transformationName LIKE :query")
    fun searchTransformations(query: String): LiveData<List<Transformation>>

    @Query("SELECT * FROM planets_table WHERE planetName LIKE :query")
    fun searchPlanets(query: String): LiveData<List<Planet>>

    @Query("SELECT * FROM character_table WHERE id = :characterId")
    fun searchCharacterById(characterId: Int): LiveData<Character?>

    @Query("SELECT * FROM character_table WHERE characterName = :characterName")
    fun searchCharacterByName(characterName: String): LiveData<Character?>

    @Query("SELECT * FROM character_table WHERE gender = :gender")
    fun getCharacterByGender(gender: String): LiveData<List<Character>>

}