package com.example.mojopediadbzedition

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mojopediadbzedition.data.Repository
import com.example.mojopediadbzedition.data.local.CharacterDatabase
import com.example.mojopediadbzedition.data.model.Character
import com.example.mojopediadbzedition.data.remote.DbzApi
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Instanz der lokalen Datenbank
    private val database = CharacterDatabase.getDatabase(application)
    // Instanz vom Repo, das die Datenbank- und API-Zugriffe übergeben kriegt
    private val repository: Repository = Repository(DbzApi, database)

    val characters: LiveData<List<Character>> = repository.characters

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository aktualisieren
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fail Load Characters $e")}
        }
    }
}