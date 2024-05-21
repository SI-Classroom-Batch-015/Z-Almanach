package com.example.mojopediadbzedition

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mojopediadbzedition.data.Repository
import com.example.mojopediadbzedition.data.local.CharacterDatabase
import com.example.mojopediadbzedition.data.remote.DbzApi
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(DbzApi, CharacterDatabase.getDatabase(application))

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository aktualisieren
            } catch (e: Exception) {
                Log.e(TAG, "Fail Load Characters $e")
            }
        }
    }
}