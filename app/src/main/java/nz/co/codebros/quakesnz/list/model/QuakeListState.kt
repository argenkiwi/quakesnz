package nz.co.codebros.quakesnz.list.model

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 27/11/2017.
 */
data class QuakeListState(
        val isLoading: Boolean,
        val features: List<Feature>?,
        val selectedFeature: Feature?
)
