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
    // Instanz vom Repo, das die Datenbank- und API-Zugriffe übergeben kriegt
    private val repository: Repository = Repository(DbzApi, database)

    // LiveData erstellt und mit LD aus dem Repo verbunden, dadurch kann in Fragmenten(durch UI-Logik) die Daten observen und auto aktu. sobald sich Daten ändern
    val characters: LiveData<List<Character>> = repository.characters
    val transformations: LiveData<List<Transformation>> = repository.transformations
    val planets: LiveData<List<Planet>> = repository.planets

    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean> get() = _startAnimation

    // Methode um DAten zu laden, werden von Fragmenten aufgerufen
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

    fun triggerAnimation() {  // Keine Hintergrundop´s
        _startAnimation.value = true
    }

    // Methode zum Suchen von Charakteren
    fun searchByCharacters(query: String) : LiveData<List<Character>> {
        return repository.searchCharacters(query)
    }

    fun searchByTransformations(query: String) : LiveData<List<Transformation>> {
        return repository.searchTransformations(query)
    }

    fun searchByPlanets(query: String) : LiveData<List<Planet>> {
        return repository.searchPlanets(query)
    }

    // Methode zum Suchen von Charakteren, Transformationen und Planeten gleichzeitig
    fun searchByAll(query: String): LiveData<List<Any>> {
        val results = MediatorLiveData<List<Any>>()
        val combinedResults = mutableListOf<Any>()

        // Charaktere suchen und zur kombinierten Liste hinzufügen
        val characters = searchByCharacters(query)
        results.addSource(characters) { charList ->
            combinedResults.clear() // Liste leeren
            charList?.let { combinedResults.addAll(it) } // Charaktere hinzufügen
            results.value = combinedResults // Ergebnis aktualisieren
        }

        // Transformationen suchen und zur kombinierten Liste hinzufügen
        val transformations = searchByTransformations(query)
        results.addSource(transformations) { transList ->
            transList?.let {
                combinedResults.addAll(it)
                results.value = combinedResults
            }
        }

        // Planeten suchen und zur kombinierten Liste hinzufügen
        val planets = searchByPlanets(query)
        results.addSource(planets) { planetList ->
            planetList?.let {
                combinedResults.addAll(it)
                results.value = combinedResults
            }
        }

        return results
    }
}
