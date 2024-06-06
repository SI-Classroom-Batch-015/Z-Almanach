package com.example.zalmanach

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zalmanach.data.Repository
import com.example.zalmanach.data.local.DragonballDatabase
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.data.remote.DbzApi
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Instanz der lokalen Datenbank
    private val database = DragonballDatabase.getDatabase(application)
    // Instanz vom Repo, das die Datenbank- und API-Zugriffe übergeben kriegt
    private val repository: Repository = Repository(DbzApi, database)

    // LiveData erstellt und mit LD aus dem Repo verbunden, dadurch kann in Fragmenten(durch UI-Logik) die Daten observen und auto aktu. sobald sich Daten ändern
    val characters: LiveData<List<Character>> = repository.characters
    val transformations: LiveData<List<Transformation>> = repository.transformations
    val planets: LiveData<List<Planet>> = repository.planets

    // MutableLiveData zum Steuern von Animationen
    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean> get() = _startAnimation

    // Methode zum Laden der Charaktere, wird von den Fragmenten aufgerufen
    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository abrufen
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fehler beim Laden der Charaktere: ${e.message}")
            }
        }
    }

    fun loadTransformations() {
        viewModelScope.launch {
            try {
                repository.getTransformations()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fehler beim Laden der Transformationen: ${e.message}")
            }
        }
    }

    fun loadPlanets() {
        viewModelScope.launch {
            try {
                repository.getPlanets()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fehler beim Laden der Planeten: ${e.message}")
            }
        }
    }

    fun triggerAnimation() {
        _startAnimation.value = true
    }

    // Methode zum Suchen von Charakteren basierend auf einer Suchanfrage
    fun searchByCharacters(query: String) : LiveData<List<Character>>{
        return repository.searchCharacters(query)
    }

    fun searchByTransformations(query: String) : LiveData<List<Transformation>>{
        return repository.searchTransformations(query)
    }

    fun searchByPlanets(query: String) : LiveData<List<Planet>>{
        return repository.searchPlanets(query)
    }
}