package com.ozantech.ozantechcaseapp.core.model.remote.response

import com.squareup.moshi.Json

data class BaseApiResponse<T>(
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "data")
    val data: T? = null,
)
