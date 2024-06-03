package com.muhammedalikocabey.exzi.core.utils

import com.muhammedalikocabey.exzi.data.model.CandleResponseModel

fun NetworkResults<List<CandleResponseModel>>.toViewEntity(
    default: List<CandleResponseModel.ViewEntity> = emptyList()
): List<CandleResponseModel.ViewEntity> {
    return when (this) {
        is NetworkResults.Success -> {
            this.data?.mapNotNull { it.toViewEntity() } ?: default
        }
        is NetworkResults.Error -> {
            this.data?.mapNotNull { it.toViewEntity() } ?: default
        }
        is NetworkResults.Loading -> {
            this.data?.mapNotNull { it.toViewEntity() } ?: default
        }
    }
}
