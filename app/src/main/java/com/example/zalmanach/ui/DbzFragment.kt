package com.example.zalmanach.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.adapter.CharacterAdapter
import com.example.zalmanach.adapter.PlanetAdapter
import com.example.zalmanach.adapter.TransformationAdapter
import com.example.zalmanach.databinding.FragmentDbzBinding

class DbzFragment : Fragment() {

    private lateinit var binding: FragmentDbzBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDbzBinding.inflate(inflater, container, false)
        viewModel.loadCharacters() // Daten schon mal Laden
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adapter für Charactere Init und Konfiguriert samt Safe Args
        val adapterCharacters = CharacterAdapter { character ->
            findNavController()
                .navigate(
                    DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                        character.characterImage,
                        character.characterName,
                        character.ki,
                        character.maxKi,
                        character.race,
                        character.descriptionSpain,
                        // Für Charactere nicht benötigt
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        false,
                    )
                )
        }
        binding.rvDbzFragmentCharacter.adapter = adapterCharacters
        viewModel.characters.observe(viewLifecycleOwner) { adapterCharacters.submitList(it) } // Beobachten und Liste an den Adapter übergeben

        // Adapter für Transformationen
        val adapterTransformations = TransformationAdapter { transformation ->
            findNavController()
                .navigate(
                    DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        transformation.transformationImage,
                        transformation.transformationName,
                        transformation.transformationKi,
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        false,
                        )
                )
        }
        binding.rvDbzFragmentTransformations.adapter = adapterTransformations
        viewModel.transformations.observe(viewLifecycleOwner) {adapterTransformations.submitList(it)}

        // Adapter für Planeten
        val adapterPlanets = PlanetAdapter { planet ->
            findNavController()
                .navigate(
                    DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        null.toString(),
                        planet.planetImage,
                        planet.planetName,
                        planet.descriptionPlanetSpain,
                        planet.isDestroyed,
                    )
                )
        }
        binding.rvDbzFragmentPlanets.adapter = adapterPlanets
        viewModel.planets.observe(viewLifecycleOwner) {adapterPlanets.submitList(it)}
    }
}