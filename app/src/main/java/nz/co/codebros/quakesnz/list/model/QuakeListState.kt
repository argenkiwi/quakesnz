package nz.co.codebros.quakesnz.list.model

import nz.co.codebros.quakesnz.core.data.Feature

data class QuakeListState(
        val isLoading: Boolean = false,
        val features: List<Feature>? = null,
        val selectedFeature: Feature? = null
)
