package com.example.zalmanach.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.zalmanach.MainViewModel
import com.example.zalmanach.adapter.SearchAdapter
import com.example.zalmanach.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Adapter Initialisieren und der RecyclerView zuweisen
        val adapter = SearchAdapter(
            emptyList(), emptyList(), emptyList(),
            { character ->
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
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            false
                        )
                    )
            },
            { transformation ->
                findNavController()
                    .navigate(
                        DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            transformation.transformationImage,
                            transformation.transformationName,
                            transformation.transformationKi,
                            "",
                            "",
                            "",
                            false
                        )
                    )
            },
            { planet ->
                findNavController()
                    .navigate(
                        DbzFragmentDirections.actionDbzFragmentToDbzDetailFragment(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            planet.planetImage,
                            planet.planetName,
                            planet.descriptionPlanetSpain,
                            planet.isDestroyed,
                        )
                    )
            }
        )

        binding.textInput.setOnQueryTextListener(this) // Setzt den Such-Listener für das Eingabefeld
        binding.rvSearchResult.adapter = adapter
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { search(it) } // Führt die Suche bei Texteingabeänderung aus
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { search(it) } // Führt die Suche bei Texteingabe einreichung aus
        return true
    }

    private fun search(query: String) {
        // Führt die Suchanfragen für Charaktere, Transformationen und Planeten aus und aktualisiert den Adapter
        viewModel.searchByCharacters(query).observe(viewLifecycleOwner) { characters ->
            viewModel.searchByTransformations(query).observe(viewLifecycleOwner) { transformations ->
                viewModel.searchByPlanets(query).observe(viewLifecycleOwner) { planets ->
                    (binding.rvSearchResult.adapter as SearchAdapter).submitLists(characters, transformations, planets)
                }
            }
        }
    }
}