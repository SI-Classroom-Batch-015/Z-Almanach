package com.example.zalmanach

import android.app.Application
import android.util.Log
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

    // Öffentliche Livedata Variable
    val characters: LiveData<List<Character>> = repository.characters
    val transformations: LiveData<List<Transformation>> = repository.transformations
    val planets: LiveData<List<Planet>> = repository.planets

    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean> get() = _startAnimation

    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository aktualisieren
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fail Load Characters ${e.message}")}
        }
    }

    fun loadTransformations() {
        viewModelScope.launch {
            try {
                repository.getTransformations()
            } catch (e: Exception) {
                Log.e(("MainViewModel"), "Fail Load Transformations ${e.message}")
            }
        }
    }

    fun loadPlanets() {
        viewModelScope.launch {
            try {
                repository.getPlanets()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fail Load Planets ${e.message}")}
        }
    }

    fun triggerAnimation() {  // Keine Hintergrundop´s
        _startAnimation.value = true
    }
}