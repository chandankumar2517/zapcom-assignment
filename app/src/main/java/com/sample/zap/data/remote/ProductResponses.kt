package com.sample.zap.data.remote

import com.sample.zap.domain.model.Item
import com.squareup.moshi.Json

data class ProductResponses(
    @Json(name = "items") val items: List<Item> = listOf(),
    @Json(name = "sectionType") var sectionType : String? = null,
)


