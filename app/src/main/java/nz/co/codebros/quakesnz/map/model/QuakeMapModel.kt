package nz.co.codebros.quakesnz.map.model

import nz.co.vilemob.rxmodel.StateEventModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuakeMapModel @Inject constructor() :
    StateEventModel<QuakeMapState, QuakeMapEvent>(QuakeMapState(), QuakeMapReducer)
