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
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation
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

        // Adapter init. und der RV hinzufügen
        val adapter = SearchAdapter(emptyList()) { result ->
            when (result) {
                is Character -> {
                    findNavController()
                        .navigate(
                            SearchFragmentDirections.actionSearchFragmentToDbzDetailFragment(
                                result.characterImage,
                                result.characterName,
                                result.ki,
                                result.maxKi,
                                result.race,
                                result.descriptionSpain,
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                false
                            )
                        )
                }
                is Transformation -> {
                    findNavController()
                        .navigate(
                            SearchFragmentDirections.actionSearchFragmentToDbzDetailFragment(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                result.transformationImage,
                                result.transformationName,
                                result.transformationKi,
                                "",
                                "",
                                "",
                                false
                            )
                        )
                }
                is Planet -> {
                    findNavController()
                        .navigate(
                            SearchFragmentDirections.actionSearchFragmentToDbzDetailFragment(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                result.planetImage,
                                result.planetName,
                                result.descriptionPlanetSpain,
                                result.isDestroyed,
                            )
                        )
                }
            }
        }
        binding.textInput.setOnQueryTextListener(this)
        binding.rvSearchResult.adapter = adapter
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            search(it)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            search(it)
        }
        return true
    }

    private fun search(query: String) {
        viewModel.searchByAll(query).observe(viewLifecycleOwner) { results ->
            results?.let {
                (binding.rvSearchResult.adapter as SearchAdapter).submitList(it)
            }
        }
    }
}