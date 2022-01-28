package com.rickrip.rickandmortyappv2.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.EpisodeCarouselItemBinding
import com.rickrip.rickandmortyappv2.databinding.EpisodeListItemBinding
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.epoxy.ViewBindingKotlinModel

class EpisodeListEpoxyController: PagingDataEpoxyController<Episode>() {

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeListItemEpoxyModel(
            episode = item!!,
            onClick = { episodeId ->
                //todo
            }
        ).id("episodeList_${item.id}")
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick:(Int) -> Unit
    ): ViewBindingKotlinModel<EpisodeListItemBinding>(R.layout.episode_list_item){

        override fun EpisodeListItemBinding.bind(){
            episodeNameTextView.text = episode.episode
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"

            root.setOnClickListener { onClick(episode.id) }
        }
    }

}