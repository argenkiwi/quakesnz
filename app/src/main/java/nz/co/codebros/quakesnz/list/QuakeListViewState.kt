package nz.co.codebros.quakesnz.list

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 18/11/2017.
 */
data class QuakeListViewState(
        val isLoading: Boolean,
        val features: List<Feature>? = null,
        val selectedFeature: Feature? = null,
        val error: Throwable? = null
)