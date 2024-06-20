package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.zalmanach.MainActivity
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.R
import com.example.zalmanach.data.model.Favorite
import com.example.zalmanach.data.model.ItemType
import com.example.zalmanach.databinding.FragmentDbzDetailBinding

class DbzDetailFragment : Fragment() {

    private lateinit var binding: FragmentDbzDetailBinding
    private val args: DbzDetailFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDbzDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ----------------------------- Daten Initialisieren --------------------------------------
        // Character
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


        // --------------------------------- DBZFragment Bezug -----------------------------------
        // Charactere: Wenn vorhanden, werden Daten angezeigt
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

                // Aktualisiert die Sichtbarkeit der Hardcode-TextViews
                tvDetailHardcodeName.visibility = if (tvDetailName.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeKi.visibility = if (tvKi.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeMaxKi.visibility = if (tvMaxKi.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeRace.visibility = if (tvRace.visibility == View.VISIBLE) View.VISIBLE else View.GONE
            }
        }

        // Transformationen
        if (transformationName.isNotEmpty()) {
            binding.apply {
                ivDetailImage.load(transformationImage)
                ivDetailImage.visibility = View.VISIBLE
                tvDetailName.text = transformationName
                tvKi.text = transformationKi

                tvDetailName.visibility = View.VISIBLE
                tvKi.visibility = View.VISIBLE

                tvDetailHardcodeName.visibility = if (tvDetailName.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeKi.visibility = if (tvKi.visibility == View.VISIBLE) View.VISIBLE else View.GONE
            }
        }

        // Planeten
        if (planetName.isNotEmpty()) {
            binding.apply {
                ivDetailImage.load(planetImage)
                ivDetailImage.visibility = View.VISIBLE
                tvDetailName.text = planetName
                tvDescription.text = planetSpainDescription

                tvDetailName.visibility = View.VISIBLE
                tvDescription.visibility = View.VISIBLE

                tvDetailHardcodeName.visibility = if (tvDetailName.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeRace.visibility = if (tvRace.visibility == View.VISIBLE) View.VISIBLE else View.GONE

                if (isDestroyed) {
                    tvIsDestroyed.visibility = View.VISIBLE
                }
            }
        }


        // ------------------------------- FavoriteFragment Bezug --------------------------------
        // Mit ENUM Typen Unterscheiden, mittels addToFavorite übergeben
        binding.ivSelectedFavorite.setOnClickListener {
            Toast.makeText(requireContext(), "Favorisiert", Toast.LENGTH_SHORT).show()
            val favorite = when {
                characterName.isNotEmpty() -> Favorite(
                    favoriteType = ItemType.CHARACTER,
                    favoriteName = characterName,
                    favoriteImage = characterImage,
                    favoriteId = 0,
                    )
                transformationName.isNotEmpty() -> Favorite(
                    favoriteType = ItemType.TRANSFORMATION,
                    favoriteName = transformationName,
                    favoriteImage = transformationImage,
                    favoriteId = 0,
                )
                planetName.isNotEmpty() -> Favorite(
                    favoriteType = ItemType.PLANET,
                    favoriteName = planetName,
                    favoriteImage = planetImage,
                    favoriteId = 0,
                )
                else -> null
            }
            favorite?.let { viewModel.addToFavorite(it) }
        }


        // --------------------------------- PlayFragment Bezug -----------------------------------
        // Setzt Spiel-Character, oder Transformation, kein Planet
        if (characterName.isNotEmpty() || transformationName.isNotEmpty()) {
            binding.ivVsDetailToPlay.setOnClickListener {
                val selectedPlayerImage =
                    if (characterName.isNotEmpty()) characterImage else transformationImage
                val selectedPlayerName =
                    if (characterName.isNotEmpty()) characterName else transformationName
                viewModel.setPlayCharacter(selectedPlayerImage, selectedPlayerName)

                findNavController().navigate(R.id.action_dbzDetailFragment_to_playFragment)
            }
        }
    }
}
