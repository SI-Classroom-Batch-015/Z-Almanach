package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.zalmanach.MainActivity
import com.example.zalmanach.MainViewModel
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

                // Aktualisiert die Sichtbarkeit der Hardcode-TextViews
                tvDetailHardcodeName.visibility = if (tvDetailName.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeKi.visibility = if (tvKi.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeMaxKi.visibility = if (tvMaxKi.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                tvHardcodeRace.visibility = if (tvRace.visibility == View.VISIBLE) View.VISIBLE else View.GONE
            }
        }

        // Transformationen: Wenn vorhanden, werden Daten angezeigt.
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

        // Planeten: Wenn vorhanden, werden Daten angezeigt
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
        // Setzt Bild und Namen übers VM im FavoriteFragment
        binding.ivFavoriteDefault.setOnClickListener {
            val selectedCharacterImage = args.imageCharacter
            val selectedCharacterName = args.nameCharacter
            viewModel.setSelectedCharacter(selectedCharacterImage, selectedCharacterName)
            binding.ivFavoriteSelected.visibility = View.VISIBLE
        }

        // Bottom Nav ausblenden, Fun in der Main
        (activity as MainActivity).showBottomNav(false)
    }

    // Bottom Nav wieder einblenden
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav(true)
    }
}
