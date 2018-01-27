package nz.co.codebros.quakesnz.list

import ar.soflete.cycler.BaseModel

/**
 * Created by Leandro on 27/01/2018.
 */
class QuakeListModel : BaseModel<QuakeListState, QuakeListEvent>(QuakeListState(false), QuakeListReducer())