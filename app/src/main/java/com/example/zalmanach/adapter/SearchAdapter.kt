package com.example.zalmanach.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
import com.example.zalmanach.databinding.ListItemSearchBinding

class SearchAdapter(
    private var datasetCharacter: List<Character>,
    private var datasetTransformation: List<Transformation>,
    private var datasetPlanet: List<Planet>,
    // Callback
    private val onCharacterSelected: (Character) -> Unit,
    private val onTransformationSelected: (Transformation) -> Unit,
    private val onPlanetSelected: (Planet) -> Unit,
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    // Kombinierte Liste, die alle Elemente von Charakteren, Transformationen und Planeten enthält
    private var combinedList: List<Any> = emptyList()

    inner class SearchViewHolder(val binding: ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    //Methode um die Listen zu aktualisieren und zu kombinieren
    @SuppressLint("NotifyDataSetChanged")
    fun submitLists(characters: List<Character>, transformations: List<Transformation>, planets: List<Planet>) {
        // Aktualisiert die Originaldatenlisten
        this.datasetCharacter = characters
        this.datasetTransformation = transformations
        this.datasetPlanet = planets
        // Kombiniert die Listen zu einer einzigen Liste
        combineLists()
        // Benachrichtigt den Adapter, dass sich die Daten geändert haben
        notifyDataSetChanged()
        Log.d("SearchAdapter", "Daten aktualisiert. Anzahl der Elemente: ${combinedList.size}")
    }

    // Kombiniert die drei Listen
    private fun combineLists() {
        // Fügt die Elemente der drei Listen in eine kombinierte Liste
        combinedList = datasetCharacter + datasetTransformation + datasetPlanet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int = combinedList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = combinedList[position]
        // Überprüft, um welche Art von Objekt es sich handelt, und setzt die entsprechenden Daten
        when (item) {
            is Character -> {
                // Setzt die Bild- und Textdaten für Charaktere
                holder.binding.ivSearchResponse.load(item.characterImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = item.characterName
                holder.itemView.setOnClickListener {
                    onCharacterSelected(item) // Ruft die Callback-Funauf
                }
            }
            is Transformation -> {
                holder.binding.ivSearchResponse.load(item.transformationImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = item.transformationName
                holder.itemView.setOnClickListener {
                    onTransformationSelected(item)
                }
            }
            is Planet -> {
                holder.binding.ivSearchResponse.load(item.planetImage) {
                    error(R.drawable.error404)
                    transformations(CircleCropTransformation())
                }
                holder.binding.tvSearchResponse.text = item.planetName
                holder.itemView.setOnClickListener {
                    onPlanetSelected(item)
                }
            }
        }
    }
}