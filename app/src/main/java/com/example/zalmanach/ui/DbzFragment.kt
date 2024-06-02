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

        // Adapter Init und Konfiguriert samt Safe Args
        val adapter = CharacterAdapter { character ->
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
                        // isDestroyed ist im grafen auf false gesetzt
                        null.toString(),
                    )
                )
        }
        binding.rvDbzFragmentCharacter.adapter = adapter
        viewModel.characters.observe(viewLifecycleOwner) { adapter.submitList(it) } // Beobachten und Liste an den Adapter übergeben

        // Zum Testen der Recyclervies und Vorbereitung für Transformation und Planets
        binding.rvDbzFragmentTransformations.adapter = adapter
        binding.rvDbzFragmentPlanets.adapter = adapter
    }
}