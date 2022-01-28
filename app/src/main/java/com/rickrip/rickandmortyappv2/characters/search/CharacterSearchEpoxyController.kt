package com.rickrip.rickandmortyappv2.characters.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.CharacterListItemBinding
import com.rickrip.rickandmortyappv2.databinding.LocalExceptionBinding
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.epoxy.LoadingEpoxyModel
import com.rickrip.rickandmortyappv2.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterSearchEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
) : PagingDataEpoxyController<Character>() {

    var localException:CharacterSearchPagingSource.LocalException? = null
        set(value){
            field = value
            if(localException !=null){
                requestModelBuild() // epoxy model update
            }
        }

    override fun buildItemModel(
        currentPosition: Int,
        item: Character?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item!!.image,
            name = item!!.name,
            onCharacterSelected = {
                onCharacterSelected(it)
            }
        ).id("charactersList_${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        localException?.let {
            LocalExceptionEpoxyModel(it).id("error").addTo(this)
            return
        }

        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        super.add(models)
    }
}

data class CharacterGridItemEpoxyModel(
    val characterId: Int,
    val imageUrl: String,
    val name: String,
    val onCharacterSelected: (Int) -> Unit
) : ViewBindingKotlinModel<CharacterListItemBinding>(R.layout.character_list_item) {
    override fun CharacterListItemBinding.bind() {
        Picasso.get().load(imageUrl).into(characterImageView)
        characterNameTextView.text = name

        root.setOnClickListener {
            onCharacterSelected(characterId)
        }
    }
}

data class LocalExceptionEpoxyModel(
    val localException: CharacterSearchPagingSource.LocalException
): ViewBindingKotlinModel<LocalExceptionBinding>(R.layout.local_exception){

    override fun LocalExceptionBinding.bind() {
        errorDescription.text = localException.details
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

}