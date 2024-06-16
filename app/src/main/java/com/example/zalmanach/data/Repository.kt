package com.example.zalmanach.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.zalmanach.data.local.DragonballDatabase
import com.example.zalmanach.data.remote.DbzApi
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Characters
import com.example.zalmanach.data.model.FavoriteItem
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Planets
import com.example.zalmanach.data.model.Transformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: DbzApi,
    private val database: DragonballDatabase
) {

    // ------------------------- Stellt Live Data Objekte fürs ViewModel ---------------------------

    private val _characters: LiveData<List<Character>> = database.dragonballDao.getAllCharacter()
    val characters: LiveData<List<Character>>
        get() = _characters

    private val _transformations: LiveData<List<Transformation>> = database.dragonballDao.getAllTransformations()
    val transformations: LiveData<List<Transformation>>
        get() = _transformations

    private val _planets: LiveData<List<Planet>> = database.dragonballDao.getAllPlanets()
    val planets: LiveData<List<Planet>>
        get() = _planets

    private val _favoriteItems: LiveData<List<FavoriteItem>> = database.dragonballDao.getAllFavorites()
    val favoriteItem: LiveData<List<FavoriteItem>>
        get() = _favoriteItems

    // ----------------------- Suche in der Datenbank, "return" das Ergebnis -----------------------
    fun searchCharacters(query: String): LiveData<List<Character>> {
        return database.dragonballDao.searchCharacters("%$query%")
    }

    fun searchTransformations(query: String): LiveData<List<Transformation>> {
        return database.dragonballDao.searchTransformations("%$query%")
    }

    fun searchPlanets(query: String): LiveData<List<Planet>> {
        return database.dragonballDao.searchPlanets("%$query%")
    }

    fun getCharactersByGender(gender: String): LiveData<List<Character>> {
        return database.dragonballDao.getCharacterByGender(gender)
    }

    fun getFavorites(): LiveData<List<FavoriteItem>> {
        return database.dragonballDao.getAllFavorites()
    }


    // ---------------------------- Favoriten Hinzufügen und Entfernen -----------------------------
    suspend fun addToFavorite(favoriteItem: FavoriteItem) {
        withContext(Dispatchers.IO) {
            database.dragonballDao.insertFavorite(favoriteItem)
        }
    }

    suspend fun removeFromFavorite(itemFavoriteId: Int, itemFavoriteType: String) {
        withContext(Dispatchers.IO) {
            database.dragonballDao.deleteFavorite(itemFavoriteId, itemFavoriteType)
        }
    }


    // ---------------------- API zu laden und in die Datenbank speichern (aSy) --------------------
    suspend fun getCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val charactersResponse: Characters = api.retrofitService.getCharacters()
                val characterList: List<Character> = charactersResponse.listOfCharacters
                database.dragonballDao.deleteAll()
                database.dragonballDao.insertCharacters(characterList)
            } catch (e: Exception) {
                Log.e("Repository", "Error Load characters: ${e.message}")
            }
        }
    }

    suspend fun getTransformations() {
        withContext(Dispatchers.IO) {
            try {
                val transformationList: List<Transformation> =
                    api.retrofitService.getTransformations()
                database.dragonballDao.deleteAllTransformations()
                database.dragonballDao.insertTransformations(transformationList)
            } catch (e: Exception) {
                Log.e("Reposirotry", "Error Load Transformations: ${e.message}")
            }
        }
    }

    suspend fun getPlanets() {
        withContext(Dispatchers.IO) {
            try {
                val planetResponse: Planets = api.retrofitService.getPlanets()
                val planetList: List<Planet> = planetResponse.listOfPlanets
                database.dragonballDao.deleteAllPlanets()
                database.dragonballDao.insertPlanets(planetList)
            } catch (e: Exception) {
                Log.e("Reposirotry", "Error Load Planets: ${e.message}")
            }
        }
    }
}