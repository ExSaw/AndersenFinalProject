package com.rickrip.rickandmortyappv2.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.CharacterListItemBinding
import com.rickrip.rickandmortyappv2.epoxy.LoadingEpoxyModel
import com.rickrip.rickandmortyappv2.epoxy.ViewBindingKotlinModel
import com.rickrip.rickandmortyappv2.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterListPagingEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
) :
    PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item!!.image,
            name = item!!.name,
            onCharacterSelected = onCharacterSelected
        ).id("charactersList_${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

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
