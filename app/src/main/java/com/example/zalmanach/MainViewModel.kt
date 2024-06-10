package com.example.zalmanach

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    // Instanz vom Repo, das die Datenbank- und API-Zugriffe verwaltet
    private val repository: Repository = Repository(DbzApi, database)

    // LiveData erstellt und mit LD aus dem Repo verbunden, dadurch kann in Fragmenten(durch UI-Logik) die Daten observen und auto aktu. sobald sich Daten ändern
    val characters: LiveData<List<Character>> = repository.characters
    val transformations: LiveData<List<Transformation>> = repository.transformations
    val planets: LiveData<List<Planet>> = repository.planets

    // MutableLiveData für die Animation
    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean> get() = _startAnimation

    // Methode zum Triggern der Animation
    fun triggerAnimation() {
        _startAnimation.value = true // Startet die Ani
    }

    // Methode um Daten zu laden, werden in Fragmenten aufgerufen
    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository holen
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

    // "searchByAll" wird vom Benuzter im Fragment ausgelöst. MediatorLiveData, kann mehrere andere LiveData-Quellen überwachen
    fun searchByAll(query: String): LiveData<List<Any>> {
        val results = MediatorLiveData<List<Any>>()
        val combinedResults = mutableListOf<Any>()

        // Charaktere suchen; mit addSource die LiveData-Quelle hinzufügen; und zur kombinierten Liste hinzufügen
        val characters = searchByCharacters(query)
        results.addSource(characters) { charList ->
            combinedResults.clear()
            charList?.let { combinedResults.addAll(it) } // Charaktere hinzufügen
            results.value = combinedResults // Ergebnis aktualisieren
        }

        // Transformationen...
        val transformations = searchByTransformations(query)
        results.addSource(transformations) { transList ->
            transList?.let {
                combinedResults.addAll(it)
                results.value = combinedResults
            }
        }

        // Planeten...
        val planets = searchByPlanets(query)
        results.addSource(planets) { planetList ->
            planetList?.let {
                combinedResults.addAll(it)
                results.value = combinedResults
            }
        }

        return results
    }

    // Methode zum Suchen von Charakteren
    private fun searchByCharacters(query: String) : LiveData<List<Character>> {
        return repository.searchCharacters(query)
    }

    private fun searchByTransformations(query: String) : LiveData<List<Transformation>> {
        return repository.searchTransformations(query)
    }

    private fun searchByPlanets(query: String) : LiveData<List<Planet>> {
        return repository.searchPlanets(query)
    }
}
