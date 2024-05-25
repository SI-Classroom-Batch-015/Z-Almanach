package com.example.zalmanach.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.zalmanach.data.local.DragonballDatabase
import com.example.zalmanach.data.remote.DbzApi
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val api: DbzApi,
    private val database: DragonballDatabase
) {

    private val _characters: LiveData<List<Character>> = database.dragonballDao.getAllCharacter()
    val characters: LiveData<List<Character>>
        get() = _characters

    // Fun die asyncron im Hintergrund laufen kann, ohne die UI zu blockieren
    suspend fun getCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val charactersResponse: Characters = api.retrofitService.getCharacters()

                // Extrahieren der Liste von Charakteren aus der API-Antwort
                val characterList: List<Character> = charactersResponse.listOfCharacters

                database.dragonballDao.deleteAll()

                // Einfügen der neuen Charaktere in die Datenbank
                database.dragonballDao.insertCharacters(characterList)
            } catch (e: Exception) {
                Log.e("Repository", "Error Load characters: ${e.message}")
            }
        }
    }
}