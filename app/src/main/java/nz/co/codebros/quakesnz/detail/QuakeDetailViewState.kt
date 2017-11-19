package nz.co.codebros.quakesnz.detail

import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by Leandro on 20/11/2017.
 */
sealed class QuakeDetailViewState

data class Failure(val throwable: Throwable) : QuakeDetailViewState()
data class Success(val feature: Feature) : QuakeDetailViewState()
