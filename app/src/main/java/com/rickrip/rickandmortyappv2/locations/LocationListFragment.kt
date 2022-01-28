package com.rickrip.rickandmortyappv2.locations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.FragmentLocationListBinding
import com.rickrip.rickandmortyappv2.episodes.EpisodeListEpoxyController
import com.rickrip.rickandmortyappv2.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class LocationListFragment:Fragment(R.layout.fragment_location_list) {

    private var _binding:FragmentLocationListBinding? = null
    private val binding:FragmentLocationListBinding by lazy {
        _binding!!
    }

    private val viewModel: LocationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLocationListBinding.bind(view)

        val epoxyController = LocationListEpoxyController()

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.submitData(it)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}