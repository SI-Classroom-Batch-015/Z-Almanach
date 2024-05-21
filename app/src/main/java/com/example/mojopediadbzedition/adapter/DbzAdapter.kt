package com.example.mojopediadbzedition.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mojopediadbzedition.R
import com.example.mojopediadbzedition.data.model.Character
import com.example.mojopediadbzedition.databinding.ListItemDbzBinding

class DbzAdapter(val onCharacterSelected: (Character) -> Unit) : // Callback der bei Klick aufgerufen wird
    RecyclerView.Adapter<DbzAdapter.CharacterViewHolder>() {

    private var dataset: List<Character> = emptyList()

    // Viewholder das einzelnes Element darstellt
    inner class CharacterViewHolder(val binding: ListItemDbzBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Character>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ListItemDbzBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int { return dataset.size }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = dataset[position]

        holder.binding.ivCharacterImage.load(character.characterImage) {
            error(R.drawable.error404)
            transformations(CircleCropTransformation())
        }
        holder.binding.tvCharacterName.text = character.characterName
        holder.itemView.setOnClickListener {
            onCharacterSelected(character)
        }
    }
}