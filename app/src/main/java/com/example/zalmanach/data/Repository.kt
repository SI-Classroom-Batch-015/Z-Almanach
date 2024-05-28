package com.example.zalmanach.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.zalmanach.data.local.DragonballDatabase
import com.example.zalmanach.data.remote.DbzApi
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Characters
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Planets
import com.example.zalmanach.data.model.Transformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: DbzApi,
    private val database: DragonballDatabase
) {

    // Live Data Objekte erstellt
    private val _characters: LiveData<List<Character>> = database.dragonballDao.getAllCharacter()
    val characters: LiveData<List<Character>>
        get() = _characters

    private val _transformations: LiveData<List<Transformation>> = database.dragonballDao.getAllTransformations()
    val transformations: LiveData<List<Transformation>>
        get() = _transformations

    private val _planets: LiveData<List<Planet>> = database.dragonballDao.getAllPlanets()
    val planets: LiveData<List<Planet>>
        get() = _planets

    // Wird vom ViewModel aufgerufen, um Daten für die Suchergebnisse abzurufen
    fun searchCharacters(query: String): LiveData<List<Character>> {
        return database.dragonballDao.searchCharacters("%$query%")
    }

    // Fun die asyncron im Hintergrund laufen kann, ohne die UI zu blockieren
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