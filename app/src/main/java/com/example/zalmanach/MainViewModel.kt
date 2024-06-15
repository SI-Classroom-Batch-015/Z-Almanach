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
import com.example.zalmanach.data.model.DbzEntity
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.data.remote.DbzApi
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //  Instanz der lokalen Datenbank
    private val database = DragonballDatabase.getDatabase(application)

    // Instanz vom Repo, das die Datenbank- und API-Zugriffe verwaltet
    private val repository: Repository = Repository(DbzApi, database)

    // LiveData erstellt und mit LD aus dem Repo verbunden
    val characters: LiveData<List<Character>> = repository.characters
    val transformations: LiveData<List<Transformation>> = repository.transformations
    val planets: LiveData<List<Planet>> = repository.planets

    // --------------- MutableLiveData`s ---------------------------------
    private val _startAnimation = MutableLiveData<Boolean>()
    val startAnimation: LiveData<Boolean>
        get() = _startAnimation

    private val _favoriteCharacterImage = MutableLiveData<String>()
    val favoriteCharacterImage: LiveData<String>
        get() = _favoriteCharacterImage

    private val _favoriteCharacterName = MutableLiveData<String>()
    val favoriteCharacterName: LiveData<String>
        get() = _favoriteCharacterName

    private val _playCharacterImage = MutableLiveData<String>()
    val playCharacterImage: LiveData<String>
        get() = _playCharacterImage

    private val _playCharacterName = MutableLiveData<String>()
    val playCharacterName: LiveData<String>
        get() = _playCharacterName

    private val _villains = MediatorLiveData<List<Any>>()
    val villains: LiveData<List<Any>>
        get() = _villains

    // --------------- PlayFragment ----------------------------------------
    fun setPlayCharacter(characterImage: String, characterName: String) {
        _playCharacterImage.value = characterImage
        _playCharacterName.value = characterName
    }

    // --------------- FavoriteFragment ----------------------------------------
    fun setFavoriteCharacter(characterImage: String, characterName: String) {
        _favoriteCharacterImage.value = characterImage
        _favoriteCharacterName.value = characterName
    }

    // --------------- HomeFragment - Triggern der Animation ---------------
    fun triggerAnimation() {
        _startAnimation.value = true // Startet die Ani
    }

    // --------------- DbzFragment - Daten dem Repo Laden ------------------
    fun loadCharacters() {
        viewModelScope.launch {
            try {
                repository.getCharacters() // Charaktere aus dem Repository holen
            } catch (e: Exception) {
                Log.e("MainViewModel", "Fail Load Characters ${e.message}")
            }
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
                Log.e("MainViewModel", "Fail Load Planets ${e.message}")
            }
        }
    }

    // --------------- SearchFragment --------------------------------------
    private fun searchByCharacters(query: String): LiveData<List<Character>> {
        return repository.searchCharacters(query)
    }

    private fun searchByTransformations(query: String): LiveData<List<Transformation>> {
        return repository.searchTransformations(query)
    }

    private fun searchByPlanets(query: String): LiveData<List<Planet>> {
        return repository.searchPlanets(query)
    }

    // "searchByAll" wird vom Benuzter im Fragment ausgelöst. MediatorLiveData, kann mehrere andere LiveData-Quellen überwachen
    fun searchByAll(query: String): LiveData<List<DbzEntity>> {
        val results = MediatorLiveData<List<DbzEntity>>()
        val combinedResults = mutableListOf<DbzEntity>()

        // Charaktere suchen; mit addSource die LiveData-Quelle; und zur kombinierten Liste hinzufügen
        val characters = searchByCharacters(query)
        results.addSource(characters) { charList ->
            combinedResults.clear()
            charList?.let { combinedResults.addAll(it) } // Charaktere hinzufügen
            results.value = combinedResults              // Ergebnis aktualisieren
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

    // --------------- PlayFragment ---------------------------------------

    // Transformations LiveData als Referenz fürs Hinzufügen der Daten
    init {
        _villains.addSource(transformations) { transformList ->
            val combinedList = mutableListOf<Any>()                // Neue leere Liste
            _villains.value?.let { combinedList.addAll(it) }       // Falls Wert da, zur Liste
            transformList?.let { combinedList.addAll(it) }         // Falls nicht null, zur Liste
            _villains.value = combinedList                         // Kombi. Liste als neuen Wert der MediatorLD setzen
        }
    }

    // Lädt die Methode das Gegner nach "gender" gesucht und gefiltert werden
    fun getCombinedVillains(gender: String) {
        val characterByGender = repository.getCharactersByGender(gender)
        _villains.addSource(characterByGender) { charList ->
            val combinedList = mutableListOf<Any>()
            charList?.let { combinedList.addAll(it) }
            val transformationsList = transformations.value
            transformationsList?.let { combinedList.addAll(it) }
            _villains.value = combinedList
        }
    }

}