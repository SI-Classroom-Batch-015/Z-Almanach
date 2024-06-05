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

        // Adapter init. und der RV hinzufügen
        val adapter = SearchAdapter(emptyList()) { character ->
            findNavController()
                .navigate(
                    SearchFragmentDirections.actionSearchFragmentToDbzDetailFragment(
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
        viewModel.searchCharacters(query).observe(viewLifecycleOwner) {
            it?.let {
                (binding.rvSearchResult.adapter as SearchAdapter).submitList(it)
            }
        }
    }
}