package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 16/02/2018.
 */
data class QuakeDetailState(
        val isLoading: Boolean,
        val feature: Feature?
)