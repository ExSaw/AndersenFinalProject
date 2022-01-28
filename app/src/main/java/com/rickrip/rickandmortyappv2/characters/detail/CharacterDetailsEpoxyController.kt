package com.rickrip.rickandmortyappv2.characters.detail

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.*
import com.rickrip.rickandmortyappv2.domain.models.Character
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.epoxy.LoadingEpoxyModel
import com.rickrip.rickandmortyappv2.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild() //update ui if loading
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (character == null) {
            // todo error state
            return
        }

        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)

        ImageEpoxyModel(
            imageUrl = character!!.image
        ).id("image").addTo(this)

        if (character!!.episodeList.isNotEmpty()) {
            val items = character!!.episodeList.map {
                EpisodeCarouselItemEpoxyModel(it).id(it.id)
            }
            TitleEpoxyModel(title = "Episodes:").id("title_episodes").addTo(this)
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.1f)
                .addTo(this)
        }

        OthersEpoxyModel(
            title = "Origin:",
            description = character!!.origin.name
        ).id("other_data_1").addTo(this)

        OthersEpoxyModel(
            title = "Species:",
            description = character!!.species
        ).id("other_data_2").addTo(this)

    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<CharacterDetailsHeaderBinding>(R.layout.character_details_header) {

        override fun CharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status
            when {
                gender.equals(
                    "male",
                    true
                ) -> genderImageView.setImageResource(R.drawable.ic_male_symbol)
                gender.equals(
                    "female",
                    true
                ) -> genderImageView.setImageResource(R.drawable.ic_female_symbol)
                else -> genderImageView.setImageResource(R.drawable.ic_question_mark)
            }
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<CharacterDetailsImageBinding>(R.layout.character_details_image) {
        override fun CharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(imageView)
        }
    }

    data class OthersEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<CharacterDetailsOtherBinding>(R.layout.character_details_other) {

        override fun CharacterDetailsOtherBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class TitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<TitleItemBinding>(R.layout.title_item) {

        override fun TitleItemBinding.bind() {
            titleTextView.text = title
        }
    }

    data class EpisodeCarouselItemEpoxyModel(
        val episode: Episode
    ) : ViewBindingKotlinModel<EpisodeCarouselItemBinding>(R.layout.episode_carousel_item) {

        override fun EpisodeCarouselItemBinding.bind() {
            episodeNameTextView.text = episode.episode
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
        }
    }


}