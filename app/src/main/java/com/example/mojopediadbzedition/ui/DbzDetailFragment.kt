package com.example.mojopediadbzedition.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mojopediadbzedition.databinding.FragmentDbzDetailBinding

class DbzDetailFragment : Fragment() {

    private lateinit var binding: FragmentDbzDetailBinding
    private val args: DbzDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDbzDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterImage = args.image
        val characterName = args.name
        val ki = args.ki
        val maxKi = args.maxKi
        val race = args.race
        val description = args.description

        binding.apply {
            ivCharacterImage.load(characterImage)
            tvCharacterName.text = characterName
            tvKi.text = ki
            tvMaxKi.text = maxKi
            tvRace.text = race
            tvDescription.text = description
        }
    }
}