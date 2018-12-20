package nz.co.codebros.quakesnz.list.model

import nz.co.codebros.quakesnz.core.data.Feature

data class QuakeListState(
        val isLoading: Boolean,
        val features: List<Feature>?,
        val selectedFeature: Feature?
)
