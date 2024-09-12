package com.sample.zap.data.wrapper

import com.sample.zap.data.remote.ProductResponses
import com.sample.zap.domain.model.ProductListEntity

fun List<ProductResponses>.toProductEntity(): List<ProductListEntity> {
    return this.map { response ->
        ProductListEntity(
            items = response.items.toMutableList(), // Map the items to a mutable list
            sectionType = response.sectionType ?: "" // Provide an empty string if `sectionType` is null
        )
    }
}