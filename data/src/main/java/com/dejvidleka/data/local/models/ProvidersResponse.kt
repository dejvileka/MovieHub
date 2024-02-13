package com.dejvidleka.data.local.models

    data class ProvidersResponse(
        val id: Int,
        val results: Map<String, ProviderDetails>
    )

    data class ProviderDetails(
        val link: String,
        val flatrate: List<ProviderInfo>?,
        val rent: List<ProviderInfo>?,
        val buy: List<ProviderInfo>?
    )

    data class ProviderInfo(
        val display_priority: Int,
        val logo_path: String,
        val provider_id: Int,
        val provider_name: String
    )
