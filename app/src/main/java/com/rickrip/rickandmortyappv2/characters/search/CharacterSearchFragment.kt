package com.rickrip.rickandmortyappv2.characters.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.characters.CharacterListFragmentDirections
import com.rickrip.rickandmortyappv2.databinding.FragmentCharacterSearchBinding
import com.rickrip.rickandmortyappv2.util.ConstantsAndUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterSearchFragment : Fragment() {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = CharacterSearchEpoxyController(::onCharacterSelected)

    private val viewModel: CharacterSearchViewModel by viewModels()

    private var curText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRun = Runnable {
        viewModel.submitSearchQuery(curText)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_character_search,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)


        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        binding.searchSrcText.doAfterTextChanged {
            curText = it?.toString() ?: " "
            handler.removeCallbacks(searchRun)
            handler.postDelayed(searchRun, 500L)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.localException = null
                epoxyController.submitData(it)
            }
        }

        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner) {
            it.getContent()?.let {
                Log.d(ConstantsAndUtils.LOG_TAG, it.toString())
                epoxyController.localException = it
            }
        }

    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    } //todo - navigation from search to details doesn't work

    private fun onCharacterSelected(characterId: Int) {

        val directions = CharacterListFragmentDirections
            .actionCharacterListFragmentToCharacterDetailFragment(
                characterId = characterId
            )
        findNavController().navigateUp()
        findNavController().safeNavigate(directions)
        //findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}