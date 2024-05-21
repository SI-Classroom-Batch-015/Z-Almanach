package com.example.mojopediadbzedition.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mojopediadbzedition.data.local.CharacterDatabase
import com.example.mojopediadbzedition.data.remote.DbzApi
import com.example.mojopediadbzedition.data.model.Character
import com.example.mojopediadbzedition.data.model.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: DbzApi,
    private val database: CharacterDatabase) {

    private val _characters: LiveData<List<Character>> = database.characterDao.getAllCharacter()
    val characters: LiveData<List<Character>>
        get() = _characters

    // Fun die asyncron im Hintergrund laufen kann, ohne die UI zu blockieren
    suspend fun getCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val charactersResponse: Characters = api.retrofitService.getCharacters()

                // Extrahieren der Liste von Charakteren aus der API-Antwort
                val characterList: List<Character> = charactersResponse.listOfCharacters

                database.characterDao.deleteAll()

                // Einfügen der neuen Charaktere in die Datenbank
                database.characterDao.insertCharacters(characterList)
            } catch (e: Exception) {
                Log.e("Repository", "Error Load characters: ${e.message}")
            }
        }
    }
}