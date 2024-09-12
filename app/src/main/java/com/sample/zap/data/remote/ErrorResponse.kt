package com.sample.zap.data.remote

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message") val message: String = "",
    @Json(name = "error") val error: String = ""
)
