package com.rickrip.rickandmortyappv2.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.local.App
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by lazy { //access to vm
        ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    private val navArgs:CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_character_detail,
            container,
            false
        )


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //observing data in sharedviewmodel characterByIdLiveData
        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) {

            epoxyController.character = it

            if (it == null) { //if fail
                Toast.makeText(
                    requireContext(), "Net Problem!", Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
                return@observe
            }

        }

        viewModel.fetchCharacter(characterId = navArgs.characterId) //invoke viewmodel

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

    }

}