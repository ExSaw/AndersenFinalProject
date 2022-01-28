package com.rickrip.rickandmortyappv2.epoxy

import com.rickrip.rickandmortyappv2.R
import com.rickrip.rickandmortyappv2.databinding.LoadingBinding

class LoadingEpoxyModel:ViewBindingKotlinModel<LoadingBinding>(R.layout.loading) {

    override fun LoadingBinding.bind() {
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

}