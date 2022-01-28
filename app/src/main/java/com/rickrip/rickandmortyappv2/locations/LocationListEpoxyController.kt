package com.rickrip.rickandmortyappv2.locations

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.EpisodeCarouselItemBinding
import com.rickrip.rickandmortyappv2.databinding.EpisodeListItemBinding
import com.rickrip.rickandmortyappv2.databinding.LocationListItemBinding
import com.rickrip.rickandmortyappv2.domain.models.Episode
import com.rickrip.rickandmortyappv2.domain.models.Location
import com.rickrip.rickandmortyappv2.epoxy.ViewBindingKotlinModel

class LocationListEpoxyController: PagingDataEpoxyController<Location>() {

    override fun buildItemModel(currentPosition: Int, item: Location?): EpoxyModel<*> {
        return LocationListItemEpoxyModel(
            location = item!!,
            onClick = { locationId ->
                //todo
            }
        ).id("locationList_${item.id}")
    }


    data class LocationListItemEpoxyModel(
        val location: Location,
        val onClick:(Int) -> Unit
    ): ViewBindingKotlinModel<LocationListItemBinding>(R.layout.location_list_item){

        override fun LocationListItemBinding.bind() {
            locationNameTextView.text = location.name
            locationDetailsTextView.text = "${location.dimension}\n${location.type}"

            root.setOnClickListener { onClick(location.id) }
        }
    }

}