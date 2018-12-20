package nz.co.codebros.quakesnz.detail

import androidx.lifecycle.ViewModel
import nz.co.codebros.quakesnz.detail.model.QuakeDetailModel
import javax.inject.Inject

class QuakeDetailViewModel @Inject constructor(
        val quakeDetailModel: QuakeDetailModel
) : ViewModel()
