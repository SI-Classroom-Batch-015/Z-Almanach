package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.zalmanach.R
import com.example.zalmanach.databinding.FragmentDbzDetailBinding

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

        // Die Views sollen jedes mal zurückgesetzt werden
        resetViews()

        // Character Daten
        val characterImage = args.imageCharacter
        val characterName = args.nameCharacter
        val ki = args.ki
        val maxKi = args.maxKi
        val race = args.race
        val characterSpainDescription = args.descriptionCharacterSpain

        // Transformation Daten
        val transformationImage = args.imageTransformation
        val transformationName = args.nameTransformation
        val transformationKi = args.kiTransformation

        // Planeten Daten
        val planetImage = args.imagePlanet
        val planetName = args.namePlanet
        val isDestroyed = args.isDestroyed
        val planetSpainDescription = args.descriptionPlanetSpain

        // Charactere: Wenn vorhanden, werden Daten angezeigt.
        if (characterName.isNotEmpty()) {
            binding.apply {
                ivDetailImage.load(characterImage)
                ivDetailImage.visibility = View.VISIBLE
                tvDetailName.text = characterName
                tvKi.text = ki
                tvMaxKi.text = maxKi
                tvRace.text = race
                tvDescription.text = characterSpainDescription

                // Views sichtbar machen
                tvDetailName.visibility = View.VISIBLE
                tvKi.visibility = View.VISIBLE
                tvMaxKi.visibility = View.VISIBLE
                tvRace.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE
            }
        }

        // Transformationen: Wenn vorhanden, werden Daten angezeigt.
        if (transformationName.isNotEmpty()) {
            binding.apply {
                ivDetailImage.load(transformationImage)
                ivDetailImage.visibility = View.VISIBLE
                tvDetailName.text = transformationName
                tvKi.text = transformationKi

                // Views sichtbar machen
                tvDetailName.visibility = View.VISIBLE
                tvKi.visibility = View.VISIBLE
            }
        }

        // Planeten: Wenn vorhanden, werden Daten angezeigt.
        if (planetName.isNotEmpty()) {
            binding.apply {
                ivDetailImage.load(planetImage)
                ivDetailImage.visibility = View.VISIBLE
                tvDetailName.text = planetName
                tvDescription.text = planetSpainDescription

                // Views sichtbar machen
                tvDetailName.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE

                if (isDestroyed) {
                    tvIsDestroyed.visibility = View.VISIBLE
                }
            }
        }

//        // Günstiger Toast falls Daten von der API fehlen
//        if (characterName.isEmpty() && transformationName.isEmpty() && planetName.isEmpty()) {
//            Toast.makeText(requireContext(), "Einige Charakterdaten sind nicht verfügbar", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun resetViews() {
        binding.apply {
            ivDetailImage.setImageResource(0)
            ivDetailImage.visibility = View.GONE
            tvDetailName.visibility = View.GONE
            tvKi.visibility = View.GONE
            tvMaxKi.visibility = View.GONE
            tvRace.visibility = View.GONE
            tvDescription.visibility = View.GONE
            tvIsDestroyed.visibility = View.GONE
        }
    }
}